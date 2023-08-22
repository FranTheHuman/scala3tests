object MatchTypes extends App {

  def lasDigitOf(number: BigInt): Int = (number % 10).toInt

  def lastCharOf(string: String): Char = string.last

  // if string.isEmpty then throw new NoSuchElementException
  // else string.last // string.charAt(string.length - 1)
  def lastElementOf[T](list: List[T]): T =
    if (list.isEmpty) throw new NoSuchElementException
    else list.last

  // is repetition but not related by types !!!!!!

  // Scala 2 ? Can't
  // Scala 3 ? Can :D

  type ConstituentPartOf[T] = T match {
    case BigInt => Int
    case String => Char
    case List[t] => t
  }

  val aDigit: ConstituentPartOf[BigInt]      = 2   // ok
  val aChar: ConstituentPartOf[String]       = 'a' // ok
  val aElement: ConstituentPartOf[List[Int]] = 42  // ok


  // UNIFICATION OF 3 METHODS INTO 1
  def lastComponentOf[T](biggerValue: T): ConstituentPartOf[T] = biggerValue match
    case number: BigInt => (number % 10).toInt
    case string: String => string.last
    case list: List[t]  => list.last

  val lastDigit = lastComponentOf(BigInt(1234567890))   // 0
  val lastChar  = lastComponentOf("Hello")  // 'o'
  val lastElement = lastComponentOf(List(1, 2, 3))     // 3

  // ---------- WHY WE NEED THISS ????? ----------

  // - Are perfect for methods that depend on the type of the input return unrelated types (Checked at compile time)

  // ---------- WHY IS DIFFERENT FROM OOP ????? ----------

  // - def returnLastConstituentPartOf(thing: Any): ConstituentPart
  // I can make a patter match for param but not for return type and loose type safety !!!!

  // ---------- WHY IS DIFFERENT FROM REGULAR GENERICS ????? ----------

  def lastElementOfList[A](list: List[A]): A = list.last

  lastElementOfList(List(1, 2, 3)) // 3
  lastElementOfList(List("a", "b", "c")) // "c"

  // In this case, the compiler knows at that very moment that what it is going to return is the same as what is going in.
    // String -> String
    // Int -> Int
  // Instead, MatchType allows the compiler to be more flexible for the type it returns.
    // String -> Char
    // BigInt -> Int

  // --------------- Recursion ---------------
  type LowerLevelPartOf[T] = T match
    case List[t] => LowerLevelPartOf[t]
    case _       => T

  val lastElementOfNestedList: LowerLevelPartOf[List[List[List[Int]]]] = 42

  // Illegal cyclic reference: type LowerLevelPartOf is a subtype of itself
  // type AnnoyingMatchType[T] = T match
  //   case _      => AnnoyingMatchType[T]

  // Compile but ...
  type InfiniteRecursiveType[T] = T match
    case Int => InfiniteRecursiveType[Int]

  def aNaiveMethod[T]: InfiniteRecursiveType[T] = ???

  // Recursion Limit Exceeded: InfiniteRecursiveType
  // val illegal: Int = aNaiveMethod[Int]

  // SCALA COMPILER LIMITATION ----
  //def accumulate[T](accumulator: T, smallerValue: ConstituentPartOf[T]): T = accumulator match
  //  case b: BigInt => b + smallerValue ---- "Flow Typing"

}
