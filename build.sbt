
//uncomment this; build and then comment it back
// Commented: For Maven usage. Also check that 'use plugin registry' is checked (settings -> Build,.. -> Maven)
//ThisBuild / useCoursier := false

name := "hiveThreeUDF"
scalaVersion := "2.13.5"
organization := "enriqueJimenez"
version := "0.1"


libraryDependencies +=  "org.scalatest" %% "scalatest" % "3.0.8" % Test

libraryDependencies += "com.eed3si9n" %% "sbt-assembly" % "sbt0.10.1_0.6" from "https://repo1.maven.org/maven2/com/eed3si9n/sbt-assembly_2.8.1/sbt0.10.1_0.6/sbt-assembly_2.8.1-sbt0.10.1_0.6.jar"

Compile / unmanagedJars := (baseDirectory.value ** "*.jar").classpath