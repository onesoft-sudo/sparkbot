import org.gradle.jvm.tasks.Jar

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"
    
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

version = "0.3.0"

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")

    implementation("net.dv8tion:JDA:5.0.0-alpha.22") {
        exclude("opus-java")
    }

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    // Define the main class for the application.
    mainClass.set("org.onesoftnet.spark.AppKt")
}

tasks.jar {
    manifest {
        attributes(mapOf(
            "Implementation-Title" to rootProject.name,
            "Implementation-Version" to project.version,
            "Main-Class" to "org.onesoftnet.spark.AppKt"
        ))
    }

    archiveBaseName.set(rootProject.name)
}

val fatJar = task("fatjar", type = Jar::class) {
    baseName = "${project.name}-build"
    manifest {
        attributes["Implementation-Title"] = "SparkBot"
        attributes["Implementation-Version"] = "1.0.0"
        attributes["Main-Class"] = "org.onesoftnet.spark.AppKt"
    }

    from(configurations.runtimeClasspath.get().map({ if (it.isDirectory) it else zipTree(it) }))

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    with(tasks.jar.get() as CopySpec)
}