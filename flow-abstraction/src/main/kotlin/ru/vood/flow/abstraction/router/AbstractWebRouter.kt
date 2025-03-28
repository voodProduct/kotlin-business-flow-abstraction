package ru.vood.flow.abstraction.router

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter
import kotlin.reflect.KClass

//abstract class AbstractWebRouter<T:Any, R:Any>(
//    handlers: List<IHandler<T, R>>
//) : AbstractRouter<T, R, HandlerId<T, R>, IHandler<T, R>>(handlers) {
//
//
//    suspend inline fun <reified TT : T, reified RR : R> run(
//        crossinline data: suspend () -> TT,
//    ): R {
//        val route = route<TT, RR>(data, {
//            HandlerId(TT::class as KClass<T>, RR::class as KClass<R>)
//        })
//
//        return route
//    }
//
//
//}