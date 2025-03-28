package ru.vood.flow.inOut

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.byInOutClass.AbstractRouterByInOutClass

@Service
class HandlerRouter(
    iWorkerList: List<IHandler<*, *>>
) : AbstractRouterByInOutClass<Any, Any, HandlerId<Any, Any>, IHandler<Any, Any>>(
    iWorkerList.map { it as IHandler<Any, Any> }
) {
    final suspend inline fun <reified IT : Any, reified IR : Any> handle(
        crossinline data: suspend () -> IT,
    ): IR {
        return mapData(data) { HandlerId(IT::class, IR::class) }
    }

}