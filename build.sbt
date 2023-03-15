enablePlugins(ScalaJSPlugin)

name := "Password Generator"
scalaVersion := "3.2.2"

scalaJSUseMainModuleInitializer := false
Global / onChangedBuildSource := ReloadOnSourceChanges
libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.1.0"
libraryDependencies += ("org.scala-js" %%% "scalajs-java-securerandom" % "1.0.0").cross(CrossVersion.for3Use2_13)
libraryDependencies += "com.raquo" %%% "laminar" % "0.14.5"
libraryDependencies += "org.scalameta" %% "munit" % "1.0.0-M7" % Test
scalacOptions += "-explain"
