# Slick code generator

Run `sbt slickGenerate` to generate slick code for your existing database schema. It will connect to the database specified in `project/Build.scala` and generate slick definitions for it, into a single scala source file to be included in your application. A console message will say the location of that scala source file, and you can copy it into your application.

(adapted from the official slick codegen example)
