import com.twitter.scrooge.ScroogeSBT.autoImport.scroogeThriftSourceFolder
import sbt.Keys.resolvers
import sbt._

lazy val client: Project = (project in file("client"))
  .settings(
    libraryDependencies ++= Seq(
      Common.Deps.spec2,
      "kondaurov.msg_service" %% "thrift" % "0.0.1-SNAPSHOT"
    )
  )
  .settings(Common.scalaSettings)

lazy val service: Project = (project in file("service"))
  .settings(
    libraryDependencies ++= Seq(
      //  "com.twitter" %% "scrooge-core" % "4.19.0",
//      "thrift" % "msg-service-interface" % "0.0.1-SNAPSHOT",
      Common.Deps.typeconfig,
      Common.Deps.spec2,
      Common.Deps.snippets
    ),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.bintrayRepo("kondaurovdev", "maven")
    ),
    scroogeThriftSourceFolder in Compile := baseDirectory.value / "target" / "thrift_external",
    scroogeThriftDependencies in Compile := Seq(
      "msg-service-interface"
    ),
    packageName in Docker := "msg_service/msg",
    version in Docker := "0.1"
  )
  .enablePlugins(JavaAppPackaging)
  .settings(Common.scalaSettings)
  .dependsOn(interface)

lazy val interface: Project = (project in file("interface"))
  .settings(
    organization := "thrift",
    name := "msg-service-interface",
    version := "0.0.1-SNAPSHOT",
    crossVersion := CrossVersion.Disabled,
    autoScalaLibrary := false,
    resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "thrift"
  )
  .disablePlugins(ScroogeSBT)

lazy val rest: Project = (project in file("rest"))
  .settings(
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % "7.0.0",
      Common.Deps.akka_http,
      Common.Deps.snippets
    ),
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.bintrayRepo("kondaurovdev", "maven")
    )
  )
  .settings(Common.scalaSettings)
  .dependsOn(interface)

lazy val rest_finagle = (project in file("rest-finagle"))
  .settings(
    libraryDependencies ++= Seq(
      "com.twitter" %% "finagle-http" % "7.0.0"
    )
  )
  .settings(Common.scalaSettings)
  .dependsOn(interface)



