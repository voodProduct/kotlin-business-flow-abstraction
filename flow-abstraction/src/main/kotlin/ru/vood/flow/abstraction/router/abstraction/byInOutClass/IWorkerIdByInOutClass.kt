package ru.vood.flow.abstraction.router.abstraction.byInOutClass

import ru.vood.flow.abstraction.router.abstraction.IWorkerId
import kotlin.reflect.KClass

interface IWorkerIdByInOutClass<out T: Any, out R: Any>: IWorkerId {

    val tkClass: KClass<out T>
    val rkClass: KClass<out R>
}