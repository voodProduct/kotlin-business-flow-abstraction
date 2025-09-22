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

/**
 * Абстрактный базовый класс для тестирования сериализации/десериализации DTO классов.
 *
 * @param dtoPackage пакет, в котором производится поиск DTO классов
 * @param annotatedWith класс аннотации, которой должны быть помечены тестируемые DTO
 * @param serializer функция для сериализации объекта в строку JSON
 * @param deserializer функция для десериализации строки JSON обратно в объект
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class AbstractAllDtoSerializationTest(
    val dtoPackage: String,
    val annotatedWith: KClass<out Annotation>,
    val serializer: (dto: Any) -> String,
    val deserializer: (json: String, dtoClass: KClass<out Any>) -> Any
) {


    /**
     * Тест проверяет полный цикл сериализации и десериализации для DTO классов.
     * Создает экземпляр DTO, сериализует его в JSON и затем десериализует обратно.
     * Ожидается, что исходный и десериализованный объекты будут равны.
     */
    @ParameterizedTest
    @MethodSource("getAllSerialisationClasses")
    fun checkAllDtoSerialization(clazz: KClass<out Any>) {
        // Создаем экземпляр DTO класса с помощью Arranger
        val value =
            assertDoesNotThrow(message = "Не удалось создать экземпляр класса ${clazz.simpleName} с помощью Arranger") {
                Arranger.some(
                    clazz.java
                )
            }
        // Проверяем полный цикл сериализации/десериализации
        assertDoesNotThrow(message = "Ошибка при сериализации/десериализации для класса ${clazz.simpleName}") {
            val json = serializer(value)
            val deserialized = deserializer(json, clazz)
            deserialized shouldBe value
        }
    }

    /**
     * Тест проверяет сериализацию/десериализацию для DTO с nullable полями, установленными в null.
     * Это важно для проверки обработки null значений в JSON.
     */
    @ParameterizedTest
    @MethodSource("getAllSerialisationClasses")
    fun checkAllDtoSerializationWithNullableAttributesSetNull(clazz: KClass<out Any>) {
        // Создаем экземпляр DTO и устанавливаем все nullable поля в null
        val value =
            assertDoesNotThrow(message = "Не удалось создать экземпляр класса ${clazz.simpleName} с nullable полями = null") {
                Arranger.some(clazz.java).setAllNullableFieldsToNull()
            }
        // Проверяем полный цикл сериализации/десериализации с null значениями
        assertDoesNotThrow(
            message = "Ошибка при сериализации/десериализации для класса ${clazz.simpleName} с null значениями"
        ) {
            val json = serializer(value)
            val deserialized = deserializer(json, clazz)
            deserialized shouldBe value
        }
    }

    /**
     * Возвращает список всех DTO классов для тестирования.
     * Ищет классы в указанном пакете, помеченные нужной аннотацией и являющиеся data классами.
     */
    protected open fun getAllSerialisationClasses(): List<KClass<out Any>> = Reflections(dtoPackage)
        .getTypesAnnotatedWith(annotatedWith.java) // Ищем классы с нужной аннотацией
        .map { it.kotlin } // Преобразуем Java Class в Kotlin KClass
        .filter { it.isData } // Фильтруем только data классы
}