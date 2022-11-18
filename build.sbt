import org.scoverage.coveralls.GitHubActions
val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
      name := "kniffeltest",
      version := "0.1.0-SNAPSHOT",
      scalaVersion := scala3Version,
      libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test,
      libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10",
      libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  )
import org.scoverage.coveralls.Imports.CoverallsKeys._

coverallsTokenFile :=  sys.env.get("COVERALLS_REPO_TOKEN")
coverallsService := Some(GitHubActions)

coverageHighlighting := true
coverageFailOnMinimum := false
coverageMinimumStmtTotal := 60
coverageMinimumBranchTotal := 60
coverageMinimumStmtPerPackage := 0
coverageMinimumBranchPerPackage := 0
coverageMinimumStmtPerFile := 0
coverageMinimumBranchPerFile := 0