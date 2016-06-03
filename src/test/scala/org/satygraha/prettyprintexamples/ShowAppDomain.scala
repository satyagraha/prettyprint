package org.satygraha.prettyprintexamples

object ShowAppDomain {
  
  case class Person(id: String, age: Int)
  case class Address(number: Int, street: String)
  case class PersonAddress(person: Person, address: Address)
  case class Group(persons: Seq[Person])
  
}
