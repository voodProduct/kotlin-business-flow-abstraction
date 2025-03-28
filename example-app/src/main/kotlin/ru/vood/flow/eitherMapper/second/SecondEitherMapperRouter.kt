package ru.vood.flow.eitherMapper.second

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.AbstractEitherMapperRouter
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.eitherMapper.first.IInputDataГрязная
import ru.vood.flow.eitherMapper.first.IInputDataЧистая


sealed interface IOutRestValidationError : IValidateMapperError

@Service
class SecondEitherMapperRouter(
    iWorkerList: List<IOutRestValidation>
) : AbstractEitherMapperRouter<IInputDataГрязная, IInputDataЧистая, IOutRestValidationError>(
    iWorkerList
)