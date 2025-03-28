package ru.vood.flow.inOut

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerByInOutClass

interface IHandler<
        T : Any,
        R : Any> : IWorkerByInOutClass<T, R, HandlerId<T, R>>