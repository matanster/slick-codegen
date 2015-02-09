This code (adapted from the official slick codegen example) generates slick code for an existing database schema. 
The database schema it generates slick code for, is defined in project/ alongside all necessary database connection details.

Use `sbt run` to generate the slick code for your schema. It will connect to the database and generate it. A console message will show saying the scala source file that it generates the slick code into.

From the initial official readme (not sure what is automatically v.s. manually there, may not matter):

`project/Build.scala` enables automatically as well as manually triggered code-generation. `src/main/scala/Example.scala` uses the generated code. It is usally wise to keep the generated Slick code under version control.
