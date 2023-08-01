
object Dependencies {
    object Kotlin {
        private const val group = "org.jetbrains.kotlin"
        val Reflect = Dependency(group, "kotlin-reflect", PackageVersions.Kotlin)
    }

    object Kotlinx {
        private const val group = "org.jetbrains.kotlinx"
        val Serialization = Dependency(group, "kotlinx-serialization-json-jvm", PackageVersions.Kotlinx)
    }

    object Ktor {
        private const val group = "io.ktor"
        val Core = Dependency(group, "ktor-client-core", PackageVersions.Ktor)
        val CIO = Dependency(group, "ktor-client-cio", PackageVersions.Ktor)
        val ContentNegotiation = Dependency(group, "ktor-client-content-negotiation", PackageVersions.Ktor)
        val SerializeKotlinx = Dependency(group, "ktor-serialization-kotlinx-json", PackageVersions.Ktor)
        val Serialization = Dependency(group, "ktor-client-serialization", PackageVersions.Ktor)
    }

    object Caffeine {
        private const val group = "com.github.ben-manes.caffeine"
        val Core = Dependency(group, "caffeine", PackageVersions.Caffeine)
    }

    object Coroutines {
        private const val group = "org.jetbrains.kotlinx"
        val Core = Dependency(group, "kotlinx-coroutines-core", PackageVersions.Coroutines)
    }

    object GoogleCloud {
        private const val group = "com.google.cloud"
        val Storage = Dependency(group, "google-cloud-storage", PackageVersions.GoogleCloud)
    }

    object Guava {
        private const val group = "com.google.guava"
        val Core = Dependency(group, "guava", "32.1.1-jre")
        val ListenableFuture = Dependency(group, "listenablefuture", "9999.0-empty-to-avoid-conflict-with-guava")
    }

    object Spring {
        private const val group = "org.springframework"
        val Context = Dependency(group, "spring-context", PackageVersions.Spring)
    }

    object Exposed {
        private const val group = "org.jetbrains.exposed"
        val Core = Dependency(group, "exposed-core", PackageVersions.Exposed)
        val DAO = Dependency(group, "exposed-dao", PackageVersions.Exposed)
        val JDBC = Dependency(group, "exposed-jdbc", PackageVersions.Exposed)
    }

    object LogBack {
        private const val group = "ch.qos.logback"
        val Core = Dependency(group, "logback-core", PackageVersions.Logback)
        val Classic = Dependency(group, "logback-classic", PackageVersions.Logback)
    }

    object SpringBoot {
        private const val group = "org.springframework.boot"
        val Starter = Dependency(group, "spring-boot-starter", PackageVersions.SpringBoot)
        val StarterLogging = Dependency(group, "spring-boot-starter-logging", PackageVersions.SpringBoot)
        val StarterCache = Dependency(group, "spring-boot-starter-cache", PackageVersions.SpringBoot)
        val ConfigProcessor = Dependency(group, "spring-boot-configuration-processor", PackageVersions.SpringBoot)
    }

    object Jackson {
        private const val group = "com.fasterxml.jackson.module"
        val KotlinModule = Dependency(group, "jackson-module-kotlin", PackageVersions.Jackson)
    }

    object JacksonDatatype {
        private const val group = "com.fasterxml.jackson.datatype"
        val Jsr310 = Dependency(group, "jackson-datatype-jsr310", PackageVersions.Jackson)
    }

    object AWS {
        private const val group = "com.amazonaws"
        val JavaDynamoDb = Dependency(group, "aws-java-sdk-dynamodb", PackageVersions.AwsDynamoDb)
    }

    object AWSSDK {
        private const val group = "software.amazon.awssdk"
        val DynamoDbEnhanced = Dependency(group, "dynamodb-enhanced", PackageVersions.AwsSdk)
        val Lambda = Dependency(group, "lambda", PackageVersions.AwsSdk)
    }

    object Arrow {
        private const val group = "io.arrow-kt"
        val Core = Dependency(group, "arrow-core", PackageVersions.Arrow)
//        val Coroutines = Dependency(group, "arrow-fx-coroutiness", PackageVersions.Arrow)
    }

    object FigureTechEs {
        private const val group = "tech.figure.eventstream"
        val Api = Dependency(group, "es-api", PackageVersions.PbEventStream)
        val ApiModel = Dependency(group, "es-api-model", PackageVersions.PbEventStream)
        val Cli = Dependency(group, "es-cli", PackageVersions.PbEventStream)
        val Core = Dependency(group, "es-core", PackageVersions.PbEventStream)
    }

    object FigureTechWallet {
        private const val group = "tech.figure.hdwallet"
        val Core = Dependency(group, "hdwallet", PackageVersions.FigureHdWallet)
    }

    object Provenance {
        private const val group = "io.provenance"
        val ProtoKotlin = Dependency(group, "proto-kotlin", PackageVersions.PbProto)
    }

    object ProvenanceClient {
        private const val group = "io.provenance.client"
        val GrpcClient = Dependency(group, "pb-grpc-client-kotlin", PackageVersions.PbGrpcClient)
    }

    object OkHttp3 {
        private const val group = "com.squareup.okhttp3"
        val Core = Dependency(group, "okhttp", PackageVersions.Okhttp)
    }

    object Moshi {
        private const val group = "com.squareup.moshi"
        val Core = Dependency(group, "moshi", PackageVersions.Moshi)
    }

    object Protobuf {
        private const val group = "com.google.protobuf"
        val Java = Dependency(group, "protobuf-java", PackageVersions.Protobuf)
    }
}