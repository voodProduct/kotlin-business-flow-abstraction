package ru.vood.flow.abstraction.router.abstraction.byInClass

import ru.vood.flow.abstraction.router.abstraction.IWorkerId
import kotlin.reflect.KClass

interface IWorkerIdByInClass<out T : Any> : IWorkerId {
    val tkClass: KClass<out T>
}