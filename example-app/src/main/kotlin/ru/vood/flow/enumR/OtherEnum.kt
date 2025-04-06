package ru.vood.flow.enumR

import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorkerId

enum class OtherEnum : IEnumWorkerId<OtherEnum> {
    OtherEnum_Q;

    override fun emun(): OtherEnum = this
}
