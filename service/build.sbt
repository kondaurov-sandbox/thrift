//parallelExecution in Test := false

libraryDependencies ++= Seq(
  //  "com.twitter" %% "scrooge-core" % "4.19.0",
  "kondaurov.msg_service" % "msg-service-interface" % "0.0.1-SNAPSHOT",
  Common.Deps.typeconfig,
  Common.Deps.spec2,
  Common.Deps.snippets
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("kondaurovdev", "maven")
)

scroogeThriftSourceFolder in Compile := baseDirectory.value / "target" / "thrift_external"
scroogeThriftDependencies in Compile := Seq(
  "msg-service-interface"
)

packageName in Docker := "msg_service/msg"
version in Docker := "0.1"

enablePlugins(JavaAppPackaging)

Common.commonSettings