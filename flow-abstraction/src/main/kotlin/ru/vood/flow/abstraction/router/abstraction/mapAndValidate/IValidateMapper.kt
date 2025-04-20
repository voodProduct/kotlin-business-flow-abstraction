package ru.vood.flow.abstraction.router.abstraction.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IValidateMapper<
        TT : Any,
        RR : Any,
        ERR : IValidateMapperError> :
    IWorker<TT, Either<NonEmptyList<ERR>, RR>, ValidateMapperId<TT, RR, ERR>> {

    override suspend fun doWork(data: TT, wId: ValidateMapperId<TT, RR, ERR>): Either<NonEmptyList<ERR>, RR> {
        return handle(data)
    }

    suspend fun handle(data: TT): Either<NonEmptyList<ERR>, RR>

}