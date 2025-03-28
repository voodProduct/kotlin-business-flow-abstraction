package ru.vood.flow.eitherMapper

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.AbstractEitherMapperRouter
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError


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