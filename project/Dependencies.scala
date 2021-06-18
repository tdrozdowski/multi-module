import sbt._
import Keys._

object Dependencies {
  val zioVersion          = "1.0.9"
  val zioPreludeVersion   = "1.0.0-RC5"
  val zioJsonVersion      = "0.1.5"
  val quillVersion        = "3.7.1"
  val postgresJdbcVersion = "42.2.22"
  val zioMagicVersion     = "0.3.3"
  val chimneyVersion      = "0.6.1"

  object Compile {

    val zioDependencies = Seq(
      "dev.zio"              %% "zio"         % zioVersion,
      "dev.zio"              %% "zio-json"    % zioJsonVersion,
      "dev.zio"              %% "zio-macros"  % zioVersion,
      "dev.zio"              %% "zio-prelude" % zioPreludeVersion,
      "io.github.kitlangton" %% "zio-magic"   % zioMagicVersion
    )

    val chimneyDependencies = Seq(
      "io.scalaland" %% "chimney" % chimneyVersion
    )

    val quillDependencies = Seq(
      "io.getquill"   %% "quill-jdbc-zio" % quillVersion,
      "org.postgresql" % "postgresql"     % postgresJdbcVersion
    )
  }

  object Test {

    val zioTestDependencies = Seq(
      "dev.zio" %% "zio-test"          % zioVersion,
      "dev.zio" %% "zio-test-sbt"      % zioVersion % "it,test",
      "dev.zio" %% "zio-test-magnolia" % zioVersion % "it,test"
    )
  }

  import Compile._

  lazy val `core-dependencies` = libraryDependencies ++= zioDependencies ++ chimneyDependencies ++
    Test.zioTestDependencies

  lazy val `core-db-dependencies` = libraryDependencies ++= quillDependencies ++ zioDependencies ++
    Test.zioTestDependencies
}
