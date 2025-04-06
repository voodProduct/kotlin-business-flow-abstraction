package ru.vood.flow.enumR.other

import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorkerId

enum class OtherEnum : IEnumWorkerId<OtherEnum> {
    OtherEnum_Q;

    override fun enumValue(): OtherEnum = this
}
