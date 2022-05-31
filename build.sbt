ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.0"
lazy val root = (project in file("."))
  .settings(
    name := "PTR-1"
  )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.6.18",
  "com.typesafe.akka" %% "akka-http" % "10.2.7",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.18",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4",
  "ch.qos.logback" % "logback-classic" % "1.2.10",
  "com.lightbend.akka" %% "akka-stream-alpakka-sse" % "3.0.4",
  "org.json4s" %% "json4s-native" % "4.0.5",
  "org.json4s" %% "json4s-jackson" % "4.0.5"
)
