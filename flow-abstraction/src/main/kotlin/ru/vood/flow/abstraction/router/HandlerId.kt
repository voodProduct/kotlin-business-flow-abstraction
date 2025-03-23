package ru.vood.flow.abstraction.router

import ru.vood.flow.abstraction.router.abstraction.IRoutedIdByInOutClass
import kotlin.reflect.KClass

data class HandlerId<T: Any, R: Any> (
    override val tkClass: KClass<T>,
    override val rkClass: KClass<R>
) : IRoutedIdByInOutClass<T, R>