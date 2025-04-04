package ru.vood.flow.inClass

import ru.vood.flow.abstraction.router.abstraction.byInClass.IWorkerByInClass
import ru.vood.flow.inClass.dto.IInputDto

interface IHandlerByInClass<
        T : IInputDto> : IWorkerByInClass<T, String, HandlerByInClassId<T>>