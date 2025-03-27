package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IValidateMapperError

interface IValidateMapper<TT : Any, out RR : Any, out ERR : IValidateMapperError> :
    IWorker<ValidateMapperId<TT, RR, ERR>> {
    override suspend fun <T, R> doWork(data: T): R {
        return handle(data as TT) as R
    }

    suspend fun handle(data: TT): Either<NonEmptyList<ERR>, RR>


}