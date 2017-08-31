name := "msg-service-interface"
version := "0.0.2-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-thrift" % "7.0.0"
)

crossVersion := CrossVersion.Disabled
autoScalaLibrary := false
resourceDirectory in Compile := baseDirectory.value / "src" / "main" / "thrift"

Common.commonSettings
Common.publishSettings
disablePlugins(ScroogeSBT)
enablePlugins(JavaAppPackaging)