package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

class EitherMapperRouter(
    iWorkerList: List<IValidateMapper<Any, Any, IValidateMapperError>>
) : AbstractRouter<ValidateMapperId<Any, Any, IValidateMapperError>, IValidateMapper<Any, Any, IValidateMapperError>>(iWorkerList) {

    suspend inline fun <reified T : Any, reified R : Any, reified ERR : IValidateMapperError> mapData(crossinline data: suspend () -> T): Either<NonEmptyList<ERR>, R> {
        return route<T, R>(
            data = data,
            workerIdExtractor = {
                ValidateMapperId(T::class, R::class, ERR::class)
            }) as Either<NonEmptyList<ERR>, R>
    }
}