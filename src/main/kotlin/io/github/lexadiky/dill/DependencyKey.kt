package io.github.lexadiky.dill

import kotlin.reflect.KType
import kotlin.reflect.typeOf

data class DependencyKey<T: Any>(val qualifier: String? = null, val type: KType) {

    companion object {

        inline fun <reified T: Any> of(qualifier: String? = null): DependencyKey<T> {
            return DependencyKey(qualifier, typeOf<T>())
        }
    }
}