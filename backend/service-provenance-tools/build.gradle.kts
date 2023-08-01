plugins {
    id("com.brashidnia.kotlin-library-conventions")
}

buildscript {
    repositories {
        google()
    }
}

allprojects {
    val project = this
    group = "com.brashidnia.provenance.tools"

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

object CustomSpec {
    val JVM_VERSION = JavaVersion.VERSION_11
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
//            allWarningsAsErrors = true
        }
    }

    configurations {
        all {
            exclude(group = "com.google.guava", module = "listenablefuture")
        }
    }
}

java {
    sourceCompatibility = CustomSpec.JVM_VERSION
    targetCompatibility = CustomSpec.JVM_VERSION
}