import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.brashidnia.kotlin-library-conventions")
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm")
}

group = "com.brashidnia.provenance.tools"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = SdkVersions.Java

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
    implementation(project(":shared"))

	// Kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Provenance
//	implementation("io.provenance.client:pb-grpc-client-kotlin:1.1.1")
    listOf(
        // Kotlin
        Dependencies.Kotlin.Reflect,
        // Kotlinx
        Dependencies.Kotlinx.Serialization,
        // Ktor
        Dependencies.Ktor.Core,
        Dependencies.Ktor.CIO,
        Dependencies.Ktor.ContentNegotiation,
        Dependencies.Ktor.Serialization,
        Dependencies.Ktor.SerializeKotlinx,
        // Jackson
        Dependencies.Jackson.KotlinModule,
        Dependencies.JacksonDatatype.Jsr310,
        // GC
        Dependencies.GoogleCloud.Storage,
        Dependencies.Guava.Core,
//        Dependencies.Guava.ListenableFuture,
    ).map { it.implementation(this) }
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = SdkVersions.Java.toString()
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
