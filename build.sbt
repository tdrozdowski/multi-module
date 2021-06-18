inThisBuild(
  List(
    organization := "dev.xymox",
    scalaVersion := "2.13.6",
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework"),
    scalacOptions += "-Ymacro-annotations"
  )
)

lazy val `core` = (project in file("core"))
  .configs(IntegrationTest)
  .settings(
    name := "core",
    Dependencies.`core-dependencies`,
    Defaults.itSettings
  )
  .dependsOn(`core-db`)

lazy val `core-db` = (project in file("core-db"))
  .configs(IntegrationTest)
  .settings(
    name := "core-db",
    Dependencies.`core-db-dependencies`,
    Defaults.itSettings
  )

lazy val root = (project in file("."))
  .settings(
    publishArtifact := false
  )
  .aggregate(
    `core`,
    `core-db`
  )
