import java.io.File

object NewTypes extends App {

  // 1- ----------------------------------- LITERAL types -----------------------------------
  val aNumber: Int = 42
  val three: 3 = 3

  def passNumber(n: Int) = println(n)

  passNumber(45)
  passNumber(three)

  def passThree(n: 3) = println(n)

  passThree(three)
  // passThree(45) - not compile

  val pi: 3.14 = 3.14
  val truth: true = true
  val myFavouriteLanguage: "Scala" = "Scala"

  def doSomethingWithYourLif(meaning: Option[42]) = meaning.foreach(println)

  // 2- ----------------------------------- UNION types -----------------------------------
  def ambivalentMethod(arg: String | Int) = arg match {
    case _: Int => println("int")
    case _: String => println("string")
  }

  ambivalentMethod(aNumber)
  ambivalentMethod(myFavouriteLanguage)

  type ErrorOr[T] = T | String

  def handleResources(file: ErrorOr[File]): Unit = {
    // your code here
    ()
  }

  val stringOrInt = if (43 > 0) "a string" else 43
  val aStringOrInt: String | Int = if (43 > 0) "a string" else 43

  // 2- ----------------------------------- INTERSECTION types -----------------------------------
  trait Camera {
    def takePhoto() = println("snap")

    def use() = println("used")
  }

  trait Phone {
    def call() = println("ring")

    def use() = println("used")
  }

  def useSmartDevice(sp: Camera & Phone) = {
    sp.takePhoto()
    sp.call()
  }

  class SmartPhone extends Camera with Phone {
    override def use(): Unit = println("used smart phone")
  }

  useSmartDevice(new SmartPhone)

  // ------

  trait HostConfig

  trait HostController {
    def get: Option[HostConfig]
  }

  trait PortConfig

  trait PortController {
    def get: Option[PortConfig]
  }

  def getConfigs(controller: HostController & PortController): Option[HostConfig & PortConfig] = {
    val configs: Option[HostConfig & PortConfig] = controller.get
    configs
  }

}
