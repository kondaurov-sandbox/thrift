version := "0.0.1-SNAPSHOT"

libraryDependencies ++= Seq(
  Common.Deps.akka_http,
  Common.Deps.snippets
)

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("kondaurovdev", "maven")
)

Common.commonSettings