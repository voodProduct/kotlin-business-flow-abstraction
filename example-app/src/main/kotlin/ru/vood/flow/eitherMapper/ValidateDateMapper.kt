package ru.vood.flow.eitherMapper

import arrow.core.Either
import arrow.core.NonEmptyList
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.MapperId
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapper
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.ValidateMapperId
import java.time.Instant

sealed interface IDateMapper: IValidateMapperError

@Service
class ValidateDateMapper : IValidateMapper<Instant, String, IDateMapper> {
    override suspend fun handle(data: Instant): Either<NonEmptyList<IDateMapper>, String> {
        return Either.Right(data.toString())
    }

    override val workerId: ValidateMapperId<Instant, String, IDateMapper>
        get() = ValidateMapperId(Instant::class, String::class, IDateMapper::class)
}