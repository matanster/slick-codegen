object Tables extends{ // or just use object demo.Tables, which is hard-wired to the driver stated during generation
  val profile = scala.slick.driver.MySQLDriver
} with slickGenerated.Tables
import Tables._
import Tables.profile.simple._

object Example extends App {
  // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
  val dbName = "articlio"
  val url = s"jdbc:mysql://localhost:3306/$dbName"
  val db = Database.forURL(url, driver = "com.mysql.jdbc.Driver")

  // Using generated code. Our Build.sbt makes sure they are generated before compilation.
}
