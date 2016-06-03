# prettyprint

Scala Pretty Print facility for case classes!

## Overview

Start with an import:
```scala
import org.satyagraha.prettyprint.Show._
```

Then have some case classes:
```scala
  case class Person(id: String, age: Int)
  case class Address(number: Int, street: String)
  case class PersonAddress(person: Person, address: Address)
  case class Group(persons: Seq[Person])
```

Then try some simple conversions:
```scala
  val p1 = Person("fred", 40)
// p1: Person = Person(fred,40)

  p1.pretty
// res0: String = id : fred, id.age : 40

  println(p1.pretty)
// id : fred, id.age : 40

  println(p1.pretty("p1"))
// p1.id : fred, p1.id.age : 40
```

Then with a nested case class:
```scala
  val a1 = Address(20, "High Street")
// a1: Address = Address(20,High Street)

  val pa1 = PersonAddress(p1, a1)
// pa1: PersonAddress = PersonAddress(Person(fred,40),Address(20,High Street))

  println(pa1.pretty)
// person.id : fred, person.id.age : 40, person.address.number : 20, person.address.number.street : High Street

  println(pa1.pretty("pa1"))
// pa1.person.id : fred, pa1.person.id.age : 40, pa1.person.address.number : 20, pa1.person.address.number.street : High Street
```

We can also get at the detailed results if we want finer control:
```scala
pa1.prettySeq(Some("pa1")) foreach println
// (fred,List(pa1, person, id))
// (40,List(pa1, person, id, age))
// (20,List(pa1, person, address, number))
// (High Street,List(pa1, person, address, number, street))
```

And representing types not already supported via their existing `toString` method is easy via typeclasses:
```scala
  import scala.concurrent.duration.FiniteDuration
// import scala.concurrent.duration.FiniteDuration

  val fd = FiniteDuration(1234, "ms")
// fd: scala.concurrent.duration.FiniteDuration = 1234 milliseconds

  implicit val showFiniteDuration = showToString[FiniteDuration]
// showFiniteDuration: org.satyagraha.prettyprint.Show[scala.concurrent.duration.FiniteDuration] = org.satyagraha.prettyprint.Show$$anon$1@5d25c391

  println(fd.pretty("fd"))
// fd : 1234 milliseconds
```

And representing types not already supported in a customised way is easy via typeclasses:
```scala
  class Demo(val something: Int)
// defined class Demo

  import org.satyagraha.prettyprint.Show
// import org.satyagraha.prettyprint.Show

  implicit val showDemo = new Show[Demo] {
    override def show(value: Demo, prefixes: Seq[String]) =
      Seq((s"something has value: ${value.something}", prefixes))
  }
// showDemo: org.satyagraha.prettyprint.Show[Demo] = $anon$1@4d87623e

  val demo = new Demo(1984)
// demo: Demo = Demo@7c13fe77

  println(demo.pretty("demo"))
// demo : something has value: 1984
```

## Acknowledgements
- Shapeless for everything: https://github.com/milessabin/shapeless 
- Tut for validated markdown: https://github.com/tpolecat/tut


