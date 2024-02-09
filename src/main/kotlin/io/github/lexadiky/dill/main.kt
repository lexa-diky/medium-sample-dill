package io.github.lexadiky.dill


class ServiceA(private val serviceB: ServiceB) {

    fun say() = println(serviceB.data)
}

class ServiceB(val data: String)


fun main() {
    val appScope = Scope(bundle = bundle {
        single { ServiceB("base data") }

    })

    val requestScope = Scope(parent = appScope, bundle = bundle {
        single { ServiceA(get()) }
    })

    requestScope.get<ServiceA>().say()
}
