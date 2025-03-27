package ru.vood.flow.eitherMapper

import arrow.core.Either
import arrow.core.NonEmptyList
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapper
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.ValidateMapperId

sealed interface IIntMapper: IValidateMapperError

@Service
class ValidateIntIMapper : IValidateMapper<Int, String, IIntMapper> {
    override suspend fun handle(data: Int): Either<NonEmptyList<IIntMapper>, String> {
        return Either.Right(data.toString())
    }
    override val workerId: ValidateMapperId<Int, String, IIntMapper>
        get() = ValidateMapperId(Int::class, String::class, IIntMapper::class)


}