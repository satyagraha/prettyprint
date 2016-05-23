package org.satyagraha.prettyprint

import Show._

object ShowApp extends App {

  case class Person(id: String, age: Int)
  case class Address(number: Int, street: String)
  case class PersonAddress(person: Person, address: Address)
  case class Group(persons: Seq[Person])

  //  println(show(23456))
  //  println(234.show)
  //  println(show("hi there"))
  //
  val p1 = Person("fred", 40)
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

}
