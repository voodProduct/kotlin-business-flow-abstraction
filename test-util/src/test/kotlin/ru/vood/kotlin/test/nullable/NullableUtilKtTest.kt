package ru.vood.kotlin.test.nullable

import com.ocadotechnology.gembus.test.Arranger
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

data class Level_1(
    val s: String? = null,
    val i: Int? = null,
    val l: Level_2
)

data class Level_2(
    val s: String? = null,
    val i: Int? = null,
    val l: Level_3
)

data class Level_3(
    val s: String? = null,
    val i: Int? = null,
)

data class Level_Collection(
    val s: String? = null,
    val i: Int? = null,
    val list: List<Level_2>,
    val set: List<Level_2>,
    val map: Map<String, Level_2>,
)

class NullableUtilKtTest : FunSpec({
    test("test correct work") {
        val some = Arranger.some(Level_1::class.java)

        val expected = Level_1(l = Level_2(l = Level_3()))

        some shouldNotBe expected
        some.setAllNullableFieldsToNull() shouldBe expected
    }

    test("test correct work collection") {
        val some = Arranger.some(Level_Collection::class.java)

        val allNullableFieldsToNull = some.setAllNullableFieldsToNull()

        val level2Excpected = Level_2(l = Level_3())

        allNullableFieldsToNull.list.forEach {
            it shouldBe level2Excpected
        }

        allNullableFieldsToNull.set.forEach {
            it shouldBe level2Excpected
        }


        allNullableFieldsToNull.map.values.forEach {
            it shouldBe level2Excpected
        }

    }
})