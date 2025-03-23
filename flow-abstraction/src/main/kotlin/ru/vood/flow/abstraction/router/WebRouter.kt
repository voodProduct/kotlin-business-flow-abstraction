package ru.vood.flow.abstraction.router

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

class WebRouter(
    handlers: List<IHandler<Any, Any>>
) : AbstractRouter<HandlerId<Any, Any>, IHandler<Any, Any>>(handlers) {


    suspend inline fun <reified T : Any, reified R : Any> run(
        crossinline data: suspend () -> T,
    ): R {
        val route = route<T, R>(data, {
            val handlerId = HandlerId(T::class, R::class)
            handlerId as HandlerId<Any, Any>
        })

        return route
    }


}