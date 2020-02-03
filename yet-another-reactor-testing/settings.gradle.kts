pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenCentral()
  }
  val kotlinVersion: String by extra
  val lombokGradlePluginVersion: String by extra
  val versionsGradlePluginVersion: String by extra
  pluginManagement {
    plugins {
      kotlin("jvm") version kotlinVersion
      id("io.franzbecker.gradle-lombok") version lombokGradlePluginVersion
      id("com.github.ben-manes.versions") version versionsGradlePluginVersion
    }
  }
}

val name: String by extra
rootProject.name = name
