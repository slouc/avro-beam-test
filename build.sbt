ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "avro-beam-test"
  )

libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.8.2", // "1.11.1" works fine
  "org.apache.beam" % "beam-runners-direct-java" % "2.40.0",
  "joda-time" % "joda-time" % "2.10.14", // needed for avro 1.8.2
  "org.slf4j" % "slf4j-api"  % "1.7.36",
  "org.slf4j" % "slf4j-log4j12" % "1.7.36"
)