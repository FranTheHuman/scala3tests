object GivenUsing extends App {

  case class Person(surname: String, name: String, age: Int)

  object StandarValues {
    // why? much cleaner code
    given standardPersonOrdering: Ordering[Person] with
      override def compare(x: Person, y: Person): Int =
        x.surname.compareTo(y.surname)
  }

  def someMethodRequiringStandarOrdering(persons: List[Person])(using ordering: Ordering[Person]): List[Person] =
    persons.sorted

  // imports
  // import StandarValues.standardPersonOrdering // 1. specific given

  import StandarValues.given Ordering[Person] // 2. by type
  //  import StandarValues.{given _} // 3. all givens

  someMethodRequiringStandarOrdering(
    persons = List(
      Person("Weasley", "Ron", 20),
      Person("Potter", "Harry", 20)
    )
  )

  // deriving givens

  // working with options
  // create a given Ordering[Option[T]] if we had a Ordering[T] in scope

  given optionOrdering[T] (using noormalOrdering: Ordering[T]): Ordering[Option[T]] with {
    override def compare(x: Option[T], y: Option[T]): Int = (x, y) match {
      case (None, None) => 0
      case (None, _) => -1
      case (_, None) => 1
      case (Some(a), Some(b)) => noormalOrdering.compare(a, b)
    }
  }

  def sortThings[T](things: List[T])(using ordering: Ordering[T]) =
    things.sorted

  val maybePersons: List[Option[Person]] = List(Some(Person("Weasley", "Ron", 20)), None)
  println(maybePersons)
  println(sortThings(maybePersons))

  // where are givens useful ?
  /*
    - type classes
    - dependency injection
    - contextual abstractions, i.e. ability to use code for some types but not for others
    - type-level programming
   */

}
