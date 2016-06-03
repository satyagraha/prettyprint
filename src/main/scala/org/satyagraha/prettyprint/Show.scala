package org.satyagraha.prettyprint

import shapeless._

trait Show[T] {

  def show(value: T, prefixes: Seq[String]): Seq[(String, Seq[String])]

}

object Show extends LabelledTypeClassCompanion[Show] {

  def show[T](t: T, prefixes: Seq[String])(implicit p: Show[T]) =
    p.show(t, prefixes)

  implicit class Enricher[T](val t: T) extends AnyVal {

    def pretty(implicit p: Show[T]): String = pretty(None)(p)

    def pretty(prefix: String)(implicit p: Show[T]): String = pretty(Some(prefix))(p)

    def pretty(prefixOption: Option[String])(implicit p: Show[T]): String =
      prettySeq(prefixOption)(p) map { case (repr, prefixes) => prefixes.mkString(".") + " : " + repr } mkString (", ")

    def prettySeq(prefixOption: Option[String])(implicit p: Show[T]): Seq[(String, Seq[String])] =
      p.show(t, prefixOption.toSeq)

  }

  def showToString[T] = new Show[T] {
    override def show(value: T, prefixes: Seq[String]) =
      Seq((value.toString, prefixes))
  }

  implicit val showInt = showToString[Int]
  
  implicit val showString = new Show[String] { 
    override def show(value: String, prefixes: Seq[String]) =
      Seq((value, prefixes))
  }

  implicit def showSeq[T](implicit st: Show[T]) = new Show[Seq[T]] {
    override def show(values: Seq[T], prefixes: Seq[String]) =
      values.zipWithIndex.flatMap { case (value, index) => st.show(value, prefixes :+ s"[${index}]") }
  }

  object typeClass extends LabelledTypeClass[Show] {

    override def emptyProduct: Show[HNil] =
      new Show[HNil] {
        override def show(value: HNil, prefixes: Seq[String]) =
          Seq()
      }

    override def product[H, T <: HList](name: String, hw: Show[H], tw: Show[T]): Show[H :: T] =
      new Show[H :: T] {
        override def show(ht: H :: T, prefixes: Seq[String]) = {
          val prefixesExt = prefixes :+ name
          hw.show(ht.head, prefixesExt) ++ tw.show(ht.tail, prefixesExt)
        }
      }

    override def project[F, G](w: => Show[G], to: F => G, from: G => F): Show[F] =
      new Show[F] {
        override def show(f: F, prefixes: Seq[String]) =
          w.show(to(f), prefixes)
      }

    override def emptyCoproduct: Show[CNil] =
      new Show[CNil] {
        override def show(value: CNil, prefixes: Seq[String]) =
          Seq()
      }

    override def coproduct[L, R <: Coproduct](name: String, lw: => Show[L], rw: => Show[R]): Show[L :+: R] =
      new Show[L :+: R] {
        override def show(lr: L :+: R, prefixes: Seq[String]) = {
          val prefixesExt = prefixes :+ name
          lr match {
            case Inl(l) => lw.show(l, prefixesExt)
            case Inr(r) => rw.show(r, prefixesExt)
          }
        }
      }
  }

}
