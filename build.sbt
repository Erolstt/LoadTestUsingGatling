name := "GatlingFrapsDemo"

version := "1.0"

scalaVersion := "2.11.4"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.2"

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.1.1" % "test"

libraryDependencies += "io.gatling"            % "gatling-test-framework"    % "2.1.1" % "test"

libraryDependencies += "commons-codec" % "commons-codec" % "1.6"

enablePlugins(GatlingPlugin)