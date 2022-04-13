import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.asciidoctor.convert") version "1.5.8"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}

group = "com.brashidnia.provenance.tools"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
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
//	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Jackson
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Swagger
//	implementation("org.springdoc:springdoc-openapi-ui:1.6.7")

	// Provenance
	implementation("io.provenance.client:pb-grpc-client-kotlin:1.1.1")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Testing
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
//	outputs.dir(snippetsDir)
}

//tasks.asciidoctor {
////	inputs.dir(snippetsDir)
//	dependsOn(test)
//}