name := "quickstart"

scalaVersion := "2.12.2"

version := "1.0"

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.10.0",
  "com.twitter" %% "scrooge-core" % "4.19.0"
)