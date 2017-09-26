import sbt.Keys._
import sbt._

import bintray.BintrayKeys._

object Common {

  def publishSettings = Seq(
    organization := "kondaurov.msg_service",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("snapshots"),
      Resolver.bintrayRepo("kondaurovdev", "maven")
    ),
    publishTo := {
      if (isSnapshot.value) {
        Some("Sonatype Nexus Repository Manager" at "https://oss.sonatype.org/content/repositories/snapshots/")
      } else {
        publishTo.value
      }
    },
    bintrayRepository := "maven",
    publishArtifact in (Compile, packageDoc) := !isSnapshot.value,
    publishArtifact in (Test, packageDoc) := false,
    bintrayReleaseOnPublish := !isSnapshot.value,
    licenses += ("MIT", url("http://opensource.org/licenses/MIT")),
    credentials ++= Seq(
      Credentials(Path.userHome / ".ivy2" / ".sonatype")
    ),
    publishMavenStyle := true,
    pomExtra :=
      <url>https://github.com/kondaurov-sandbox/thrift</url>
        <scm>
          <url>https://github.com/kondaurov-sandbox/thrift.git</url>
          <connection>https://github.com/kondaurov-sandbox/thrift.git</connection>
        </scm>
        <developers>
          <developer>
            <id>kondaurovdev</id>
            <name>Alexander Kondaurov</name>
            <email>kondaurov.dev@gmail.com</email>
          </developer>
        </developers>,
    pomIncludeRepository := { _ => false }
  )

  def scalaSettings = Seq(
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    scalaVersion := "2.11.8"
  )

  object Deps {
    val spec2: ModuleID =  "org.specs2" %% "specs2-core" % "3.9.1" % "test"
    val typeconfig: ModuleID =  "com.github.kondaurovdev" %% "typeconfig" % "1.1.0-SNAPSHOT"
    val akka_http: ModuleID =  "com.github.kondaurovdev" %% "akka_http" % "1.0.0-SNAPSHOT"
    val snippets: ModuleID =  "com.github.kondaurovdev" %% "snippets" % "1.1.0-SNAPSHOT"
  }

}
