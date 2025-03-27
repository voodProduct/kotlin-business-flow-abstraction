package ru.vood.flow.abstraction.router.mapper.responceEntity

import ru.vood.flow.abstraction.router.abstraction.IWorkerId
import kotlin.reflect.KClass

data class IResponseEntityWorkerId<out T : Any>(
    val tkClass: KClass<out T>
) : IWorkerId
