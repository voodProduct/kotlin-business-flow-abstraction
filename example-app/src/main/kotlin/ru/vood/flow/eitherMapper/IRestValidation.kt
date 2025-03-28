package ru.vood.flow.eitherMapper

import arrow.core.Either
import arrow.core.NonEmptyList
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.ValidateMapperId

interface IRestValidation: IWorker<IInputDataГрязная,
        Either<NonEmptyList<IRestValidationError>, IInputDataЧистая>,
        ValidateMapperId<IInputDataГрязная, IInputDataЧистая, IRestValidationError>
        >