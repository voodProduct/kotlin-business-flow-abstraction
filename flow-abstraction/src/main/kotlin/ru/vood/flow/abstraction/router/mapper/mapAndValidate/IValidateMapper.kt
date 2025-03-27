package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerByInOutClass

interface IValidateMapperError


interface IValidateMapper<TT : Any, out RR : Any> :
    IWorkerByInOutClass<TT, RR, ValidateMapperId<TT, RR>> {
    override suspend fun <T, R> doWork(data: T): R {
        TODO("Not yet implemented")
    }

    //    override suspend fun <T, R> doWork(data: T): Either<NonEmptyList<IValidateMapperError>, R> {
//        print(this::class.java.canonicalName+" -> ")
//        return handle(data as TT) as R
//    }

    fun handle(data: TT): Either<NonEmptyList<IValidateMapperError>, RR>


}