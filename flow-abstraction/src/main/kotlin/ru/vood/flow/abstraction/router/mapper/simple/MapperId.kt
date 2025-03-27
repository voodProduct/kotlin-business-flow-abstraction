package ru.vood.flow.abstraction.router.mapper.simple

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerIdByInOutClass
import kotlin.reflect.KClass

data class MapperId<out T : Any, out R : Any>(
    override val tkClass: KClass<out T>,
    override val rkClass: KClass<out R>
) : IWorkerIdByInOutClass<T, R>
