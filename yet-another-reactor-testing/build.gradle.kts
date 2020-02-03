plugins {
  java
  application
  kotlin("jvm")
  id("io.franzbecker.gradle-lombok")
  id("com.github.ben-manes.versions")
}

val mainClass: String by project
val weldVersion: String by project
val log4j2Version: String by project
val lombokVersion: String by project
val cdiApiVersion: String by project
val jandexVersion: String by project
val junit4Version: String by project
val logbackVersion: String by project
val assertkVersion: String by project
val assertjVersion: String by project
val reactorBomVersion: String by project
val javaVersion = JavaVersion.VERSION_1_8
val junitJupiterVersion: String by project
val gradleWrapperVersion: String by project

tasks.withType(Wrapper::class.java) {
  gradleVersion = gradleWrapperVersion
  distributionType = Wrapper.DistributionType.BIN
}

sourceSets {
  main {
    java.srcDir("src/main/kotlin")
  }
  test {
    java.srcDir("src/test/kotlin")
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
  kotlinOptions {
    freeCompilerArgs += "-Xjsr305=strict"
    jvmTarget = "$javaVersion"
  }
}

java {
  sourceCompatibility = javaVersion
  targetCompatibility = javaVersion
}

repositories {
  mavenCentral()
}

lombok {
  version = lombokVersion
}

dependencies {
  implementation(platform("io.projectreactor:reactor-bom:$reactorBomVersion"))
  implementation("io.projectreactor:reactor-core")
  testImplementation("io.projectreactor:reactor-test")

  implementation(kotlin("stdlib"))
  implementation(kotlin("reflect"))
  //implementation("io.vavr:vavr:0.10.0")
  implementation("org.jboss.weld.se:weld-se-core:$weldVersion")
  implementation("javax.enterprise:cdi-api:$cdiApiVersion")
  implementation("org.jboss:jandex:$jandexVersion")
  implementation("org.apache.logging.log4j:log4j-core:$log4j2Version")
  annotationProcessor("org.projectlombok:lombok:$lombokVersion")

  testImplementation("org.assertj:assertj-core:$assertjVersion")
  testImplementation(platform("org.junit:junit-bom:$junitJupiterVersion"))
  testImplementation("org.junit.jupiter:junit-jupiter")
  testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
  testImplementation("junit:junit:$junit4Version")
}

application {
  mainClassName = mainClass
}

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging {
    showExceptions = true
    showStandardStreams = true
    events(
        org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
        org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
        org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
    )
  }
}

tasks {
  register("fatJar", Jar::class.java) {
    //archiveAppendix.set("all")
    archiveClassifier.set("all")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
      attributes("Main-Class" to mainClass)
    }
    from(configurations.runtimeClasspath.get()
        .onEach { println("add from dependencies: ${it.name}") }
        .map { if (it.isDirectory) it else zipTree(it) })
    val sourcesMain = sourceSets.main.get()
    sourcesMain.allSource.forEach { println("add from sources: ${it.name}") }
    from(sourcesMain.output)
  }
}

tasks.create<Zip>("sources") {
  dependsOn("clean")
  shouldRunAfter("clean", "assemble")
  description = "Archives sources in a zip file"
  group = "Archive"
  from("src") {
    into("src")
  }
  from(".gitignore")
  from(".java-version")
  from(".travis.yml")
  from("build.gradle.kts")
  from("README.md")
  from("settings.gradle.kts")
  archiveFileName.set("${project.buildDir}/sources-${project.version}.zip")
}

tasks {
  named("clean") {
    doLast {
      delete(
          project.buildDir,
          "${project.projectDir}/out"
      )
    }
  }
}

defaultTasks("clean", "sources", "fatJar", "installDist", "build")
