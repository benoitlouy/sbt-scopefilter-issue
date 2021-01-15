lazy val a = project

lazy val b = project.dependsOn(a % Test)
