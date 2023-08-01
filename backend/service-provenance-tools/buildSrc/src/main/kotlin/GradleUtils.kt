import org.gradle.kotlin.dsl.DependencyHandlerScope

const val SEPARATOR_DEPENDENCY = ":"

data class Dependency(
    val group: String,
    val artifact: String,
    val version: String? = null
) {
    fun implementation(scope: DependencyHandlerScope) {
        scope.also {
            it.add(
                "implementation",
                arrayOf(group, artifact, version).filterNotNull().joinToString(SEPARATOR_DEPENDENCY)
            )
        }
    }
}