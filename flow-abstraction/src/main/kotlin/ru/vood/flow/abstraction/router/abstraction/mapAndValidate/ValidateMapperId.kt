package ru.vood.flow.abstraction.router.abstraction.mapAndValidate

import ru.vood.flow.abstraction.router.abstraction.IWorkerId
import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerIdByInOutClass
import kotlin.reflect.KClass


data class ValidateMapperId<out T : Any, out R : Any, out ERR : IValidateMapperError>(
    val tkClass: KClass<out T>,
    val rkClass: KClass<out R>,
    val errkClass: KClass<out ERR>
) : IWorkerId
