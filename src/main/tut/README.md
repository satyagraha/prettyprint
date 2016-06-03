# prettyprint

Scala Pretty Print facility for case classes!

## Overview

Start with an import:
```tut:silent
import org.satyagraha.prettyprint.Show._
```

Then have some case classes:
```tut:silent
  case class Person(id: String, age: Int)
  case class Address(number: Int, street: String)
  case class PersonAddress(person: Person, address: Address)
  case class Group(persons: Seq[Person])
```

Then try some simple conversions:
```tut:book
  val p1 = Person("fred", 40)
  p1.pretty
  println(p1.pretty)
  println(p1.pretty("p1"))
```

Then with a nested case class:
```tut:book
  val a1 = Address(20, "High Street")
  val pa1 = PersonAddress(p1, a1)
  println(pa1.pretty)
  println(pa1.pretty("pa1"))
```

We can also get at the detailed results if we want finer control:
```tut:book
pa1.prettySeq(Some("pa1")) foreach println
```

And representing types not already supported via their existing `toString` method is easy via typeclasses:
```tut:book
  import scala.concurrent.duration.FiniteDuration
  val fd = FiniteDuration(1234, "ms")
  implicit val showFiniteDuration = showToString[FiniteDuration]
  println(fd.pretty("fd"))
```

And representing types not already supported in a customised way is easy via typeclasses:
```tut:book
  class Demo(val something: Int)

  import org.satyagraha.prettyprint.Show
  implicit val showDemo = new Show[Demo] {
    override def show(value: Demo, prefixes: Seq[String]) =
      Seq((s"something has value: ${value.something}", prefixes))
  }
  
  val demo = new Demo(1984)
  println(demo.pretty("demo"))
```

## Acknowledgements
- Shapeless for everything: https://github.com/milessabin/shapeless 
- Tut for validated markdown: https://github.com/tpolecat/tut


