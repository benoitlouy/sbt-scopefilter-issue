package check

import sbt._
import sbt.Keys._

object CheckPlugin extends AutoPlugin {

  override def requires: Plugins = empty

  override def trigger = allRequirements

  object autoImport {
    val checkInfo = taskKey[String]("info")
    val checkGatherInfo = taskKey[Seq[String]]("gather")
  }

  import autoImport._

  def configSettings: Seq[Def.Setting[_]] = Seq(
    checkInfo := Def.task {
      s"${moduleName.value} ${configuration.value.name}"
    }.value,
    checkGatherInfo := Def.taskDyn {
      checkInfo.all(
        ScopeFilter(
          inDependencies(ThisProject),
          inConfigurations(configuration.value)
        )
      )
    }.value
  )

  override def projectSettings: Seq[Def.Setting[_]] =
    inConfig(Compile)(configSettings) ++
      inConfig(Test)(configSettings)
}
