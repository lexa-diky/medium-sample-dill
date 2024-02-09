package io.github.lexadiky.dill

import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class Bundle(
    val bindings: Map<DependencyKey<*>, Binding<*>>
)

class BundleBuilder {
    private val bindings: MutableMap<DependencyKey<*>, Binding<*>> = HashMap()

    fun <T : Any> single(
        qualifier: String? = null,
        type: KType,
        create: DependencyProvider.() -> T
    ) {
        bindings[DependencyKey<T>(qualifier, type)] = Binding(Lifetime.Singleton, create)
    }

    fun merge(bundle: Bundle) {
        bindings += bundle.bindings
    }

    internal fun create(): Bundle {
        return Bundle(bindings)
    }
}

inline fun <reified T : Any> BundleBuilder.single(qualifier: String? = null, noinline create: DependencyProvider.() -> T) {
    single(qualifier, typeOf<T>(), create)
}

fun bundle(build: BundleBuilder.() -> Unit): Bundle {
    return BundleBuilder().apply(build)
        .create()
}

fun main() {
    val bundleB = bundle {
        single { ServiceB(get()) }
    }

    val bundleA = bundle {
        single { ServiceA(get()) }
        merge(bundleB)
    }
}