package ru.vood.flow.enumR

import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorkerId

enum class SomeEnum : IEnumWorkerId<SomeEnum> {
    SomeEnum_Q;

    override fun emun(): SomeEnum = this
}

data class INEnumRouterData(
    val i: Int
)

data class OutEnumRouterData(
    val s: String
)