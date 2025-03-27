package ru.vood.flow.abstraction.router.mapper.simple

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

abstract class AbstractMapperRouter(
    iWorkerList: List<IMapper<Any, Any>>
) : AbstractRouter<MapperId<Any, Any>, IMapper<Any, Any>>(iWorkerList) {

    suspend inline fun <reified T : Any, reified R : Any> mapData(crossinline data: suspend () -> T): R =
        route<T, R>(
            data = data,
            workerIdExtractor = {
                MapperId(T::class, R::class)
            })
}