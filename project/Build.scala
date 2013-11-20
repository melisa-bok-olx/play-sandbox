import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "testApp"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
   "com.github.tototoshi" %% "scala-csv" % "1.0.0-SNAPSHOT"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here    
      resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

  )

}
