package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorkerId

@JvmInline
@Deprecated("asd")
value class EnumWorkerId<E : Enum<E>
//        out T : Any, out R : Any
        >(
    val emun: E
//    override val tkClass: KClass<out T>,
//    override val rkClass: KClass<out R>
) : IWorkerId


interface IEnumWorkerId<E : Enum<E>> : IWorkerId {
    fun emun(): E
}