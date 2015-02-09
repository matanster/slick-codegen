/**
 * auto-generate Slick classes for a given existing database,
 * then run some code (which here really does nothing, but could be your actual application, if you want)
 *
 * Usage: sbt run
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
      slick <<= slickCodeGenTask, // register manual sbt command
      sourceGenerators in Compile <+= slickCodeGenTask // register automatic code generation on every compile, remove for only manual use
    )
  ) 

  // code generation task
  lazy val slick = TaskKey[Seq[File]]("gen-tables")
  lazy val slickCodeGenTask = (sourceManaged, dependencyClasspath in Compile, runner in Compile, streams) map { (dir, cp, r, s) =>
    val dbName = "articlio"
    val userName = "articlio"
    val password = "" // no password for this user
    val url = s"jdbc:mysql://localhost:3306/$dbName" // connection info for a pre-populated throw-away, in-memory db for this demo, which is freshly initialized on every run
    val jdbcDriver = "com.mysql.jdbc.Driver"
    val slickDriver = "scala.slick.driver.MySQLDriver"
    val pkg = "slickGenerated"
    val outputDir = (dir / dbName).getPath // place generated files in sbt's managed sources folder
    val fname = outputDir + "/slickGenerated/Tables.scala"
    println(s"\nOutput scala file: $fname\n")
    toError(r.run("scala.slick.codegen.SourceCodeGenerator", cp.files, Array(slickDriver, jdbcDriver, url, outputDir, pkg, userName, password), s.log))
    Seq(file(fname))
  }
}
