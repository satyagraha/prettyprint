package org.satygraha.prettyprintexamples

//import org.satyagraha.prettyprint._
import org.satyagraha.prettyprint.Show._

object ShowApp extends App {

  import ShowAppDomain._

  //  println(show(23456))
  //  println(234.show)
  //  println(show("hi there"))
  //
  val p1 = Person("fred", 40)
  p1.pretty
  //  println(show(p1))
  //  println(p1.show)
  //
  val a1 = Address(20, "High Street")
  //  println(show(a1))

  val pa1 = PersonAddress(p1, a1)
  // println(show(pa1))
  //println(pa1.show("pa1"))
  println(pa1.pretty("data"))
  //println(pa1.pretty())
  println(pa1.pretty)

  val pp1 = Group(Seq(Person("joe", 14), Person("sue", 20)))
  println(pp1.pretty)

  val ps1 = Seq(Person("adam", 15), Person("jane", 21))
  println(ps1.pretty)

  import scala.concurrent.duration.FiniteDuration
  val fd = FiniteDuration(1234, "ms")
  println(fd)

  implicit val showFiniteDuration = showToString[FiniteDuration]
  println(fd.pretty("fd"))

  class Demo(val something: Int)

  import org.satyagraha.prettyprint.Show
  implicit val showDemo = new Show[Demo] {
    override def show(value: Demo, prefixes: Seq[String]) =
      Seq((s"something has value: ${value.something}", prefixes))
  }
  
  val demo = new Demo(1984)
  println(demo.pretty("demo"))

}
