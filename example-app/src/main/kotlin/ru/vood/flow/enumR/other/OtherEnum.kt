package ru.vood.flow.enumR.other

import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorkerId

enum class OtherEnum : IEnumWorkerId<OtherEnum> {
    OtherEnum_Q;

    override fun enumValue(): OtherEnum = this
}




//data class OtherEnumWrapper(val value1: OtherEnum) : Enum<OtherEnum> by value
////    Enum<OtherEnum>(
////    name = value.name,
////    ordinal = value.ordinal
////)
//{
//    override fun enumValue(): OtherEnum = value
//}