object ExtensionMethods extends App {

  // type enrichment == "pimping"

  case class Person(name: String) {
    def greet: String = s"Hi, my name is $name, nice to meet you!"
  }

  implicit class PersonLike(string: String) {
    def greet: String = Person(string).greet
  }

  val danielsGreet = "Daniel".greet // new PersonLike("Daniel").greet

  // The Idea is not use implicits because will be deprecated in the future !!!!!!!!

  // extension methods == type enrichment

  extension (string: String) {
    def bye: String = s"Good Bye $string"
  }

  val danielsBye = "Daniels".bye

  // "created in a library"
  sealed abstract class Tree[+A]
  case class Leaf[+A](value: A) extends Tree[A]
  case class Branch[+A](left: Tree[A], right: Tree[A]) extends Tree[A]

  // adding methods
  extension [A](tree: Tree[A])

    def filter(predicate: A => Boolean): Boolean = tree match
      case Leaf(value)         => predicate(value)
      case Branch(left, right) => left.filter(predicate) && right.filter(predicate)

    def map[B](f: A => B): Tree[B] = tree match
      case Leaf(value) => Leaf(f(value))
      case Branch(left, right) => Branch(left.map(f), right.map(f))


  val tree: Branch[Int] = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))
  println(tree.filter(_ > 0))
  println(tree.map(_ * 10))

  extension [A](tree: Tree[A])(using ordering: Ordering[A])
    def sum(using numeric: Numeric[A]): A = tree match
      case Leaf(value)         => value
      case Branch(left, right) => numeric.plus(left.sum, right.sum)

  println(tree.sum)

}
