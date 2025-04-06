package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IWorkerId

interface IEnumWorkerId<E : Enum<E>> : IWorkerId {
    fun enumValue(): E
}