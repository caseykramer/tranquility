name := "Tranquility"

version := "0.1"

scalaVersion := "2.9.1"

testOptions := Seq(Tests.Filter(s => Seq("Spec", "Unit").exists(s.endsWith(_))))

libraryDependencies ++= Seq(
	"org.specs2" %% "specs2" % "1.6.1" % "test",
	"org.pegdown" % "pegdown" % "1.0.2" % "test",
	"org.apache.httpcomponents" % "httpclient" % "4.0-alpha4",
	"net.liftweb" % "lift-json" % "1.1-M5" 
)