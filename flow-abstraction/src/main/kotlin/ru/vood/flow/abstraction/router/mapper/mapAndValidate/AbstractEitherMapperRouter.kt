package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.AbstractRouter
import ru.vood.flow.abstraction.router.abstraction.IWorker

abstract class AbstractEitherMapperRouter<
        T : Any,
        R : Any,
        ERR : IValidateMapperError
        >(
    iWorkerList: List<IWorker<T, Either<NonEmptyList<ERR>, R>, ValidateMapperId<T, R, ERR>>>
) : AbstractRouter<
        T,
        Either<NonEmptyList<ERR>, R>,
        ValidateMapperId<T, R, ERR>,
        IWorker<T,
                Either<NonEmptyList<ERR>, R>,
                ValidateMapperId<T, R, ERR>
                >
//        IValidateMapper<T,R, IValidateMapperError>
        >(iWorkerList = iWorkerList) {

    suspend inline fun <reified IT : T, reified IR : R, reified IERR : ERR> mapData(
        crossinline data: suspend () -> IT
    ): Either<NonEmptyList<ERR>, R> {
        val validateMapperId = ValidateMapperId(IT::class, IR::class, IERR::class)
        return data().let { dataDto ->
            routedMap[validateMapperId]?.doWork(dataDto)
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $validateMapperId")
    }


}