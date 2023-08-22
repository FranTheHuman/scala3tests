object TypeLambdas extends App {

  // LEVEL 0 - INT - STRING [Attach to values]

  val aNumer: Int = 42

  // LEVEL 1 - OPTION - LIST ["Generics"]

  val aList: List[Int] = List(1, 2, 3)
  // ** List is similar to a function operating a type level. (type constructor)

  // LEVEL 2 - FUNCTOR - MONAD ["Generics of Generics"]

  class Functor[F[_]]

  val functorOption: Functor[Option] = new Functor[Option]

  // ------------------- TYPE CONSTRUCTOR -------------------
  // **
  type MyList = [T] =>> List[T] // MyList ======= List

  type MapWithStringKey = [T] =>> Map[String, T]
  val addressBook: MapWithStringKey[String] = Map()
  type MapWithStringKey2[T] = Map[String, T] // Exactly the same :/ but less powerfull :0

  type SpecialEither = [T, E] =>> Either[E, Option[T]]
  val specialEither: SpecialEither[Int, String] /* Either[String, Option[Int]] */ = Right(Some(2))

  // util for higher level types
  // like monads
  trait Monad[M[_]] {
    def pure[A](value: A): M[A]

    def flatMap[A, B](ma: M[A])(transformation: A => M[B]): M[B]
  }

  // monad for either
  // class EitherMonad[E] extends Monad[Either[E, ?]]
  // works for Either[String, String], Either[String, Int], Either[String, Person]
  class EitherMonad[E] extends Monad[[T] =>> Either[E, T]] {
    override def pure[A](value: A): Either[E, A] = ???

    override def flatMap[A, B](ma: Either[E, A])(transformation: A => Either[E, B]): Either[E, B] = ???
  }


}
