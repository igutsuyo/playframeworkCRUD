name := """sqlConectTest"""
organization := "com.exe-group"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.1"

libraryDependencies += guice

libraryDependencies ++= Seq (
    guice,
    javaJdbc,
    "org.mariadb.jdbc" % "mariadb-java-client" % "2.5.4"
)