/**
 * auto-generate Slick classes for a given existing database,
 *
 * Usage: sbt slickGenerate
 *
 */

import sbt._
import Keys._
import Tests._

object myBuild extends Build {
  lazy val mainProject = Project(
    id="main",
    base=file("."),
    settings = Project.defaultSettings ++ Seq(
      scalaVersion := "2.11.5",
      libraryDependencies ++= List(
        "com.typesafe.slick" %% "slick" % "2.1.0",
        "com.typesafe.slick" %% "slick-codegen" % "2.1.0",
        "org.slf4j" % "slf4j-nop" % "1.6.4",
        "mysql" % "mysql-connector-java" % "latest.release"
      ),
      slick <<= slickCodeGenTask // register sbt command
    )
  ) 

  // code generation task
  lazy val slick = TaskKey[Seq[File]]("slickGenerate")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val dbName = "articlio"
    val userName = "articlio"
    val password = "" // no password for this user
    val url = s"jdbc:mysql://localhost:3306/$dbName" 
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val slickDriver = "scala.slick.driver.MySQLDriver"
    val targetPackageName = "models"
    val outputDir = (dir / dbName).getPath // place generated files in sbt's managed sources folder
    val fname = outputDir + s"/$targetPackageName/Tables.scala"
    println(s"\nauto-generating slick source for database schema at $url...")
    println(s"output source file file: file://$fname\n")
    toError(r.run("scala.slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, targetPackageName, userName, password), s.log))
    Seq(file(fname))
  }
}
