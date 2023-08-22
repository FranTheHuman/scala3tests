object PathDependentTypes extends App {

  class Outer {
    class Inner

    object InnerObject

    type InnerType

    def process(inner: Inner): Unit = println(inner)

    def process_general(inner: Outer#Inner): Unit = println(inner)
  }

  val outer = new Outer
  val inner = new outer.Inner // instance-dependent type

  val outerA = new Outer
  val outerB = new Outer
  // val innerA: outerA.Inner = new outerB.Inner // not the same
  println(outerA.InnerObject == outerB.InnerObject) // false

  // ---- PATH DEPENDENT TYPES = parent of all instance-dependent types
  val innerA = new outerA.Inner
  val innerB = new outerB.Inner

  // path-dependent type = Outer#Inner
  outerA.process(innerA) // OK
  // outerA.process(innerB) NOT OK
  outerA.process_general(innerA) // OK
  outerA.process_general(innerB) // OK

  // USE CASES
  // * type-checking/type inference e.g Akka Streams Flow[Int, Int, NotUsed]#Repr
  // * type lambdas scala 2: { type T[A] = List[A] }#T
  // * type lambdas scala 3: [A] =>> List[A]
  // * type-level programming use-case

  // methods with dependent types
  trait Record {
    type Key
  }

  def getIdentifier(record: Record): record.Key = ??? // Can return a different type depending of the value

  // dependent function types
  val getIdentifierFunc = getIdentifier

  val getIdentifierFunc2 = new(Record => Record#Key) { // == extends Function1[Record, Record#Key]
    override def apply(v1: Record): v1.Key = getIdentifierFunc(v1)
  }

}
