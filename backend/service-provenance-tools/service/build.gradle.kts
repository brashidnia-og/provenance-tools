import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.brashidnia.kotlin-application-conventions")
	id("org.springframework.boot") version PackageVersions.SpringBoot
	id("io.spring.dependency-management") version PackageVersions.SpringDependency
    id("org.jetbrains.kotlin.plugin.allopen") version PackageVersions.Kotlin
    id("org.jetbrains.kotlin.plugin.spring") version PackageVersions.Kotlin
	id("org.asciidoctor.convert") version "1.5.8"
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

    // TODO upgrades for later
//	implementation("org.springframework.boot:spring-boot-starter-websocket")

//	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
//	implementation("org.springframework.boot:spring-boot-starter-jdbc")
//	implementation("org.springframework.boot:spring-boot-starter-web")
//	implementation("org.springframework.data:spring-data-rest-hal-explorer")
	// Kotlin
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Apache
	implementation("commons-io:commons-io:2.11.0")

	// Spring
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-security")
//	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Swagger
//	implementation("org.springdoc:springdoc-openapi-ui:1.6.7")

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

    developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
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

tasks.test {}

configurations {
    all {
        exclude(group = "com.google.guava", module = "listenablefuture")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
        exclude(group = "org.slf4j", module = "slf4j-log4j12")
    }
}