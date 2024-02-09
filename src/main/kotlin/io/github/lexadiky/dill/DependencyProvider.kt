package io.github.lexadiky.dill

interface DependencyProvider {

    fun <T: Any> get(dependencyKey: DependencyKey<T>): T
}

inline fun <reified T: Any> DependencyProvider.get(qualifier: String? = null): T {
    return get(DependencyKey.of(qualifier))
}