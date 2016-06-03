lazy val commonSettings = Seq(
  organization := "org.satyagraha",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "prettyprint",
	libraryDependencies ++= Seq(
  		"com.chuusai" %% "shapeless" % "2.3.1"
	)
)

tutSettings

val docs = taskKey[Unit]("builds documentation")

docs := {
  val s: TaskStreams = streams.value
  tut.value
  val src = crossTarget.value / "tut"
  val dst = file(".")
  val matches = src.listFiles.filter(_.getName.endsWith(".md")).map(f => (f, new File(dst, f.getName)))
  matches map { entry => s.log.info(s"Copying: ${entry}") }
  IO.copy(matches, overwrite = true, preserveLastModified = false)
}
