package ru.vood.flow.eitherMapper.second

import arrow.core.Either
import arrow.core.NonEmptyList
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.IValidateMapperError
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.ValidateMapperId
import ru.vood.flow.eitherMapper.first.IInputDataГрязная
import ru.vood.flow.eitherMapper.first.IInputDataЧистая
import ru.vood.flow.eitherMapper.first.InputData
import ru.vood.flow.eitherMapper.first.RestInputData

sealed interface IIntMapper : IValidateMapperError

@Service
class SecondEitherMapper : IOutRestValidation {

    override val workerId: ValidateMapperId<IInputDataГрязная, IInputDataЧистая, IOutRestValidationError>
        get() = ValidateMapperId(RestInputData::class, InputData::class, IOutRestValidationError::class)

    override suspend fun doWork(data: IInputDataГрязная): Either<NonEmptyList<IOutRestValidationError>, IInputDataЧистая> {
        return Either.Right(InputData)
    }


}