name := """play-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.webjars" % "bootstrap" % "3.2.0",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "font-awesome" % "4.1.0",
  "org.webjars" % "ionicons" % "1.5.2",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.3.0",
  "org.webjars" % "jquery" % "2.0.2",
  "org.webjars" % "angularjs" % "1.2.26",
  "org.webjars" % "angular-ui-router" % "0.2.11-1",
  "org.hornetq" % "hornetq-core-client" % "2.4.4.Final",
  "org.hornetq" % "hornetq-native" % "2.4.4.Final" from "http://repo1.maven.org/maven2/org/hornetq/hornetq-native/2.4.4.Final/hornetq-native-2.4.4.Final.jar"
)

seq(jasmineSettings : _*)

appJsDir <+= sourceDirectory { src => src / "main" / "webapp" / "static" / "js" }

appJsLibDir <+= sourceDirectory { src => src / "main" / "webapp" / "static" / "js" / "lib" }

jasmineTestDir <+= baseDirectory { _ / "test" / "js" }

jasmineConfFile <+= baseDirectory { _ / "test" / "js" / "test.dependencies.js" }

//jasmineRequireJsFile <+= sourceDirectory { src => src / "main" / "webapp" / "static" / "js" / "lib" / "require" / "require-2.0.6.js" }

//jasmineRequireConfFile <+= sourceDirectory { src => src / "test" / "js" / "require.conf.js" }

(test in Test) <<= (test in Test) dependsOn (jasmine)