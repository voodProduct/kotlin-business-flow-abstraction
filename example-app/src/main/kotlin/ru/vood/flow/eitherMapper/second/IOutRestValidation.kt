package ru.vood.flow.eitherMapper.second

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.ValidateMapperId
import ru.vood.flow.eitherMapper.first.IInputDataГрязная
import ru.vood.flow.eitherMapper.first.IInputDataЧистая

interface IOutRestValidation: IWorker<IInputDataГрязная,
        Either<NonEmptyList<IOutRestValidationError>, IInputDataЧистая>,
        ValidateMapperId<IInputDataГрязная, IInputDataЧистая, IOutRestValidationError>
        >