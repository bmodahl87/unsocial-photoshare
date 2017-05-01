name := """unsocial-photoshare"""

version := "1.99-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  evolutions,
  "mysql" % "mysql-connector-java" % "5.1.26",
  "org.apache.directory.studio" % "org.apache.commons.io" % "2.4"
)


fork in run := false