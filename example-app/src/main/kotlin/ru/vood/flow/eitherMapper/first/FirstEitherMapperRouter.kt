package ru.vood.flow.eitherMapper.first

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.AbstractEitherMapperRouter
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.IValidateMapperError


sealed interface IInputDataГрязная

data object RestInputData : IInputDataГрязная


sealed interface IInputDataЧистая

data object InputData : IInputDataЧистая

sealed interface IRestValidationError : IValidateMapperError

@Service
class FirstEitherMapperRouter(
    iWorkerList: List<IRestValidation>
) : AbstractEitherMapperRouter<IInputDataГрязная, IInputDataЧистая, IRestValidationError>(
    iWorkerList
)