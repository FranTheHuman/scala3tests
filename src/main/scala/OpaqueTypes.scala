object OpaqueTypes extends App {

  //case class Name(value: String) {
  //  // additional logic
  //}

  object SocialNetwork { // domain
    opaque type Name = String
    // Name == String

    // 1 - companion object
    object Name {
      def fromString(s: String): Option[Name] =
        if (s.isEmpty || s.charAt(0).isLower) None
        else Some(s)
    }

    // 2 - extension methods (type enrichment for add functionality)
    extension (name: Name) {
      def length: Int = name.length
    }

  }

  import SocialNetwork._

  // outside the scope of the domain name != String
  // val name: Name = "Fran"

  val name: Option[Name] = Name.fromString("Fran")
  println(name)

  val badName: Option[Name] = Name.fromString("fran")
  println(badName)

  // name.map(_.charAt(0)) cannot call any method on Name value

  val length: Option[Int] = name.map(_.length) // thanks to the extension method !!!
  println(length)

  object Graphics {
    opaque type Color = Int
    opaque type ColorFilter <: Color = Int

    val red: Color   = Color.fromRGB(255, 0, 0)
    val green: Color = Color.fromRGB(0, 255, 0)
    val blue: Color  = Color.fromRGB(0, 0, 255)

    val halfTransparency: ColorFilter = 0x88 // 50% transparency

    object Color {
      def fromRGB(r: Int, g: Int, b: Int): Color = {
        (r << 16) + (g << 8) + b
      }
    }

    extension (c: Color) {
      def red: Int = (c & 0xFF0000) >> 16
      def green: Int = (c & 0xFF00) >> 8
      def blue: Int = c & 0xFF
    }

  }

  import Graphics._
  case class OverlayFilter(c: Color)
  val fadeLayer = OverlayFilter(halfTransparency) // Color Filter extends Color

}
