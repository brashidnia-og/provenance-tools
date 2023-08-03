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
    val JVM_VERSION = SdkVersions.Java
}

subprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {

        kotlinOptions {
            jvmTarget = SdkVersions.Java.toString()
//            allWarningsAsErrors = true
        }
    }

    configurations {
        all {
            exclude(group = "com.google.guava", module = "listenablefuture")
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
            exclude(group = "org.slf4j", module = "slf4j-log4j12")
        }
    }
}

java {
    sourceCompatibility = CustomSpec.JVM_VERSION
    targetCompatibility = CustomSpec.JVM_VERSION
}