object Traits extends App {

  // ---------------- SUPER/TRANSPARENT TRAIT ------------------------

  // Scala 3 allows one to mark a trait or class as transparent, which means that it can be suppressed in type inference.

  transparent trait Paintable

  trait Color

  case object Red extends Color, Paintable
  case object Green extends Color, Paintable
  case object Blue extends Color, Paintable
  case object Yellow extends Color, Paintable
  case object Cyan extends Color, Paintable
  case object Withe extends Color, Paintable


  // SCALA 2
  val color: Color with Paintable = if (43 > 2) Red else Blue

  // TRANSPARENT TRAIT
  val color2: Color = if (43 > 2) Yellow else Cyan

  println(color)
  println(color2)

}
