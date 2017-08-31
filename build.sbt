import sbt._

lazy val client: Project = (project in file("client"))
  .settings(
    libraryDependencies ++= Seq(
      Common.Deps.spec2,
      "kondaurov.msg_service" %% "thrift" % "0.0.1-SNAPSHOT"
    )
  )
  .settings(Common.commonSettings)

lazy val service: Project = project in file("service")

lazy val interface: Project = project in file("interface")

lazy val rest: Project = (project in file("rest"))
  .dependsOn(interface)

lazy val rest_finagle = (project in file("rest-finagle"))
  .dependsOn(interface)



