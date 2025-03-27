package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IValidateMapperError

interface IValidateMapper<TT : Any, out RR : Any> :
    IWorker<ValidateMapperId<TT, RR>> {
    override suspend fun <T, R> doWork(data: T): R {
        val tt = data as TT
        val handle = handle(tt)
        return handle as R
    }

    fun handle(data: TT): Either<NonEmptyList<IValidateMapperError>, RR>


}