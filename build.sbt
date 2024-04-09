ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.9"

lazy val root = (project in file("."))
  .settings(
    name := "scala-cache",
      libraryDependencies ++= Seq(
        "com.github.cb372" %% "scalacache-caffeine"    % "0.28.0",
        "com.github.cb372" %% "scalacache-cats-effect" % "0.28.0",
      )
  )
