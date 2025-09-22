package ru.vood.kotlin.test.serialisation

import com.ocadotechnology.gembus.test.Arranger
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.reflections.Reflections
import ru.vood.kotlin.test.nullable.setAllNullableFieldsToNull
import kotlin.reflect.KClass

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractAllDtoSerializationTest(
    val dtoPackage: String,
    val annotatedWith: KClass<out Annotation>,
    val serializer: (dto: Any) -> String,
    val deserializer: (json: String, dtoClass: KClass<out Any>) -> Any
) {

    @ParameterizedTest
    @MethodSource("getAllSerialisationClasses")
    fun checkAllDtoSerialization(clazz: KClass<out Any>) {
        val value =
            assertDoesNotThrow(message = "cannot create instance of ${clazz.simpleName}") { Arranger.some(clazz.java) }
        assertDoesNotThrow(message = "asd") {
            val json = serializer(value)
            val deserialized = deserializer(json, clazz)
            deserialized shouldBe value
        }
    }

    @ParameterizedTest
    @MethodSource("getAllSerialisationClasses")
    fun checkAllDtoSerializationWithNullableAttributesSetNull(clazz: KClass<out Any>) {
        val value =
            assertDoesNotThrow(message = "cannot create instance of ${clazz.simpleName}") {
                Arranger.some(clazz.java).setAllNullableFieldsToNull()
            }
        assertDoesNotThrow(message = "asd") {
            val json = serializer(value)
            val deserialized = deserializer(json, clazz)
            deserialized shouldBe value
        }
    }

    protected open fun getAllSerialisationClasses(): List<KClass<out Any>> = Reflections(dtoPackage)
        .getTypesAnnotatedWith(annotatedWith.java)
        .map { it.kotlin }
        .filter { it.isData }
}