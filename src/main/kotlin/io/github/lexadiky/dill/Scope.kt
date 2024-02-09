package io.github.lexadiky.dill

import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap

class Scope(
    private val parent: Scope? = null,
    private val bundle: Bundle
) : Closeable, DependencyProvider {
    private val cache: MutableMap<DependencyKey<*>, Any> = ConcurrentHashMap()

    override fun <T : Any> get(dependencyKey: DependencyKey<T>): T {
        return lookup(dependencyKey) ?: error("can't find binding")
    }

    private fun <T : Any> lookup(dependencyKey: DependencyKey<T>): T? {
        val binding = bundle.bindings.get(dependencyKey)
        return if (binding != null) {
            return when (binding.lifetime) {
                Lifetime.Singleton -> cache.computeIfAbsent(dependencyKey) { binding.factory.invoke(this)!! }
                Lifetime.Provider -> binding.factory.invoke(this)
            } as T
        } else {
            parent?.lookup(dependencyKey)
        }
    }

    override fun close() {
        cache.clear()
    }
}