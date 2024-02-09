package io.github.lexadiky.dill

data class Binding<T>(
    val lifetime: Lifetime = Lifetime.Singleton,
    val factory: DependencyProvider.() -> T
)