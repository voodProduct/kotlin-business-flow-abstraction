package ru.vood.kotlin.test.nullable

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * Рекурсивно устанавливает все nullable-поля объекта и его вложенных структур (коллекций, data-классов) в `null`.
 * Non-nullable поля остаются без изменений.
 *
 * @param T тип объекта (должен быть non-nullable и иметь primary constructor).
 * @return Новый экземпляр объекта с обнулёнными nullable-полями.
 *
 * @throws IllegalArgumentException если у класса нет primary constructor.
 *
 * Пример использования:
 * ```kotlin
 * data class Person(val name: String, val age: Int?, val friends: List<Person?>)
 * val person = Person("Alice", 30, listOf(Person("Bob", null, emptyList())))
 * val processed = person.setAllNullableFieldsToNull()
 * // Результат: Person(name="Alice", age=null, friends=[Person(name="Bob", age=null, friends=[])])
 * ```
 */
fun <T : Any> T.setAllNullableFieldsToNull(): T {
    // Получаем KClass текущего объекта
    val kClass = this::class
    // Проверяем наличие primary constructor (обязательно для data-классов)
    val constructor = kClass.primaryConstructor ?: error("$kClass does not have a primary constructor")

    // Строим аргументы для конструктора, обрабатывая каждый параметр
    val args = constructor
        .parameters
        .associateWith { param ->
            val any = when {
                // Если тип параметра nullable — заменяем на null
                param.type.isMarkedNullable -> null
                // Если параметр — коллекция (List/Set/Map), обрабатываем её элементы
                param.type.isCollectionOrMap() -> {
                    // Находим соответствующее свойство класса
                    val property = kClass.memberProperties.find { it.name == param.name }
                    // Получаем значение свойства и обрабатываем коллекцию
                    property?.getter?.call(this)?.let { collection ->
                        processCollection(collection)
                    }
                }
                // Обычные поля (non-nullable, не коллекции)
                else -> {
                    // Находим свойство и его значение
                    kClass.memberProperties.find { it.name == param.name }
                        ?.getter?.call(this)?.let { value ->
                            // Если значение — data-класс, рекурсивно обрабатываем его
                            if (value::class.isData) {
                                value.setAllNullableFieldsToNull()
                            } else {
                                value
                            }
                        }
                }
            }
            any
        }
    // Создаём новый экземпляр с обработанными аргументами
    return constructor.callBy(args)
}

/**
 * Обрабатывает коллекцию (List/Set/Map), рекурсивно применяя [setAllNullableFieldsToNull] к её элементам.
 *
 * @param collection коллекция для обработки.
 * @return Новая коллекция с обнулёнными nullable-элементами.
 *
 * Примечания:
 * - Для Map обрабатываются только значения (ключи остаются без изменений).
 * - Поддерживаются только стандартные коллекции (List, Set, Map).
 */
private fun processCollection(collection: Any): Any {
    return when (collection) {
        is List<*> -> collection.map { it?.processNullableValue() }
        is Set<*> -> collection.map { it?.processNullableValue() }.toSet()
        is Map<*, *> -> collection.mapValues { (_, value) -> value?.processNullableValue() }
        else -> collection
    }
}

/**
 * Обрабатывает nullable-значение: если это data-класс, рекурсивно обнуляет его nullable-поля.
 *
 * @return Оригинальное значение (если не data-класс) или новый экземпляр с обнулёнными полями.
 */
private fun Any.processNullableValue(): Any? {
    return if (this::class.isData) {
        this.setAllNullableFieldsToNull()
    } else {
        this
    }
}

/**
 * Проверяет, является ли тип коллекцией (List, Set, Map) или их подтипом.
 *
 * @return `true`, если тип — коллекция.
 */
private fun KType.isCollectionOrMap(): Boolean {
    return when (val classifier = this.classifier) {
        is KClass<*> -> classifier.isSubclassOf(List::class) ||
                classifier.isSubclassOf(Set::class) ||
                classifier.isSubclassOf(Map::class)
        else -> false
    }
}
