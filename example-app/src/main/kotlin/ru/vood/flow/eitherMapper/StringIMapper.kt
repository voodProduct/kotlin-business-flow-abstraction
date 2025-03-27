package ru.vood.flow.eitherMapper

import arrow.core.Either
import arrow.core.NonEmptyList
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.IMapper
import ru.vood.flow.abstraction.router.mapper.MapperId
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapper
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.ValidateMapperId
import java.time.Instant

sealed interface IStringMapper: IValidateMapperError

@Service
class ValidateStringIMapper : IValidateMapper<Int, String, IStringMapper> {
    override suspend fun handle(data: Int): Either<NonEmptyList<IStringMapper>, String> {
        return Either.Right(data.toString())
    }
    override val workerId: ValidateMapperId<Int, String>
        get() = ValidateMapperId(Int::class, String::class)


}