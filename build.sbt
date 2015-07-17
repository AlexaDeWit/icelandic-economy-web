name := """icelandic-economy"""

version := "1.0-SNAPSHOT"

val scalaz = "org.scalaz" %% "scalaz-core" % "7.1.3"
val httpsDsl  = "org.http4s" %% "http4s-dsl" % "0.8.4"
val httpsBlazeClient  = "org.http4s" %% "http4s-blazeclient" % "0.8.4"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  scalaz,
  httpsDsl,
  httpsBlazeClient,
  specs2 % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
