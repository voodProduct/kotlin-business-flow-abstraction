package ru.vood.flow.inClass

import ru.vood.flow.abstraction.router.abstraction.byInClass.IWorkerIdByInClass
import ru.vood.flow.inClass.dto.IInputDto
import kotlin.reflect.KClass

data class HandlerByInClassId<T : IInputDto>(
    override val tkClass: KClass<out T>,
) : IWorkerIdByInClass<T>
