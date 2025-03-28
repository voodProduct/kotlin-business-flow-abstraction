package ru.vood.flow.eitherMapper.first

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.abstraction.mapAndValidate.ValidateMapperId

interface IRestValidation : IWorker<IInputDataГрязная,
        Either<NonEmptyList<IRestValidationError>, IInputDataЧистая>,
        ValidateMapperId<IInputDataГрязная, IInputDataЧистая, IRestValidationError>
        >