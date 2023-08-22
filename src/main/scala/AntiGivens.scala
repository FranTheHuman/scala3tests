import AntiGivens.SameType.processListSameTypeV3

object AntiGivens extends App {

  def processLists[A, B](la: List[A], lb: List[B]): List[(A, B)] =
    for {
      a <- la
      b <- lb
    } yield (a, b)


  object SameType {

    // You can modify the code
    def processListsSameType[A](la: List[A], lb: List[A]): List[(A, A)] =
      for {
        a <- la
        b <- lb
      } yield (a, b)

    // You cannot modify the code and you can use the same code
    def processListsSameTypeV2[A](la: List[A], lb: List[A]): List[(A, A)] =
      processLists[A, A](la, lb)

    // You need that compiler determine that A and B are the same type (e.g. the domain needs that A and B are the same type)
    // enforce DIFFERENT types

    // type =:=[A, B]
    def processListSameTypeV3[A, B](la: List[A], lb: List[B])(using ev: A =:= B): List[(A, B)] =
      processLists(la, lb) // compiler error

  }

  val combinedLists = processLists(List(1, 2, 3), List("a", "b", "c")) // "ok"
  val notOkLists = processLists(List(1, 2, 3), List(4, 5)) // "not ok"


  // val combinedNotSameTypeLists = processListSameTypeV3(List(1, 2, 3), List("a", "b", "c")) // "not ok"
  val okLists = processListSameTypeV3(List(1, 2, 3), List(4, 5)) // "ok"

  object DifferentTypes {

    import scala.util.NotGiven

    // compiler can't find  a given T  => compiler will generate a NotGiven[T]
    def processListsDifferentType[A, B](la: List[A], lb: List[B])(using NotGiven[A =:= B]): List[(A, B)] =
      processLists(la, lb)

    val combinedLists_ok = processListsDifferentType(List(1, 2, 3), List("a", "b", "c")) // "ok"
    // val notOkLists_shouldNotCOmpile = processListsDifferentType(List(1, 2, 3), List(4, 5)) // "not ok"

  }



}
