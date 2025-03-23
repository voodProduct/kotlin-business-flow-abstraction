package ru.vood.flow.abstraction.router.abstraction

import kotlin.reflect.KClass

interface IRoutedIdByInOutClass<T: Any, R: Any> : IWorkerId {
    val tkClass: KClass<T>
    val rkClass: KClass<R>
}