object Enums extends App {

  enum Permissions:
    case READ, WRITE, EXECUTE, NONE

  // Compiler will generate a sealed class with the values as constans that extends Permissions

  val read: Permissions = Permissions.READ

  // ------------------- WITH ARGUMENTS AND METHODS -------------------

  enum PermissionsWithBits(val bits: Int) {

    case READ extends PermissionsWithBits(4) // 100
    case WRITE extends PermissionsWithBits(2) // 010
    case EXECUTE extends PermissionsWithBits(1) // 001
    case NONE extends PermissionsWithBits(0) // 000

    def toHex: String = Integer.toHexString(this.bits)
    // can create variables -> don't recommended because can be in conflict with the immutable aspect of enums
  }

  val read2: PermissionsWithBits = PermissionsWithBits.READ
  println(read2.bits)
  println(read2.toHex)

  // ------------------- COMPANION OBJECTS -------------------

  object PermissionsWithBits {
    def fromBits(bits: Int): PermissionsWithBits =
      PermissionsWithBits.WRITE
  }

  // ------------------- STANDARD API -------------------

  val first = Permissions.READ.ordinal
  val allPermissions: Array[Permissions] = Permissions.values
  println(allPermissions.mkString(", "))

  // Only applicable for enums without arguments
  val readPermission: Permissions = Permissions.valueOf("READ") // Permissions.READ

}
