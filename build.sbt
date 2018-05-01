import sbt.Keys.organization


lazy val forassembly = project.enablePlugins(AssemblyPlugin).settings(
  scalaVersion := "2.11.12",

  organization := "io.kf.etl",

  name := "forassembly",

  version := "0.0.1",

  libraryDependencies ++=  Seq(
    "com.trueaccord.scalapb" %% "scalapb-json4s" % "0.3.3"
  ),

  assemblyShadeRules in assembly := {
    val shadePackage = "io.kf.etl.shade"
    Seq(
      ShadeRule.zap("fastparse.**").inAll,
      ShadeRule.zap("sourcecode.**").inAll,
      ShadeRule.zap("com.trueaccord.lenses.**").inAll,
      ShadeRule.zap("com.trueaccord.scalapb.*").inAll,
      ShadeRule.zap("com.trueaccord.scalapb.scalapb.**").inAll,
      ShadeRule.zap("com.trueaccord.scalapb.textformat.**").inAll,
      ShadeRule.zap("com.thoughtworks.paranamer.**").inAll,
      ShadeRule.zap("com.google.protobuf.**").inAll,
      ShadeRule.rename("org.json4s.**" -> s"${shadePackage}.org.json4s.@1").inAll,
      ShadeRule.rename("com.fasterxml.jackson.**" -> s"${shadePackage}.com.fasterxml.jackson.@1").inAll
    )
  },

  assemblyMergeStrategy in assembly := {
    case PathList("google", xs @ _*) => MergeStrategy.discard
    case PathList("scalapb", xs @ _*) => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },

  assemblyJarName in assembly := s"${name.value}-${version.value}.jar",

  publishMavenStyle := true,

  publishArtifact in Compile := false, //when publish artifact, the artifact from Compile scope will be ignored
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),

  addArtifact(artifact in assembly, assembly),

  skip in publish := true
)

lazy val forpublish = project.settings(
  scalaVersion := "2.11.12",

  organization := "io.kf.etl",

  name := "kf-scalapb-json4s-shade",

  version := "0.0.1",
  packageBin in Compile := (assembly in (forassembly, Compile)).value
)