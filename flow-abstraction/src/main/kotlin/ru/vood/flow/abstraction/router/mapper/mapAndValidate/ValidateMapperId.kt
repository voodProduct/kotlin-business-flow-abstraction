package ru.vood.flow.abstraction.router.mapper.mapAndValidate

import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerIdByInOutClass
import kotlin.reflect.KClass


data class ValidateMapperId<out T : Any, out R : Any>(
    override val tkClass: KClass<out T>,
    override val rkClass: KClass<out R>
) : IWorkerIdByInOutClass<T, R>
