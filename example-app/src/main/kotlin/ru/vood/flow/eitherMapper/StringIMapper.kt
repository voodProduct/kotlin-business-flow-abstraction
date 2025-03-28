package ru.vood.flow.eitherMapper

import arrow.core.Either
import arrow.core.NonEmptyList
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.ValidateMapperId

sealed interface IIntMapper: IValidateMapperError

@Service
class ValidateIntIMapper : IRestValidation {

    override val workerId: ValidateMapperId<IInputDataГрязная, IInputDataЧистая, IRestValidationError>
        get() = ValidateMapperId(RestInputData::class, InputData::class, IRestValidationError::class)

    override suspend fun doWork(data: IInputDataГрязная): Either<NonEmptyList<IRestValidationError>, IInputDataЧистая> {
        return Either.Right(InputData)
    }


}