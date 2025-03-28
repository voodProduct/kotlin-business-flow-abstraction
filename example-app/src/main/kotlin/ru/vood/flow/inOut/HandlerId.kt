package ru.vood.flow.inOut

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerIdByInOutClass
import kotlin.reflect.KClass

data class HandlerId<T : Any, R : Any>(
    override val tkClass: KClass<out T>,
    override val rkClass: KClass<out R>
) : IWorkerIdByInOutClass<T, R>
