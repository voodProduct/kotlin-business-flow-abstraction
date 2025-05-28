package ru.vood.flow.abstraction.util

import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

fun <T : Any> T.setAllNullableFieldsToNull(): T {
    val kClass = this::class
    val constructor = kClass.primaryConstructor ?: error("$kClass does not have a primary constructor")

    val args = constructor
        .parameters
        .associateWith { param ->
            val any = when {
                // Если параметр nullable — сразу null
                param.type.isMarkedNullable -> null
                // Если параметр коллекция (List/Set/Map) — обрабатываем элементы
                param.type.isCollectionOrMap() -> {
                    val property = kClass.memberProperties.find { it.name == param.name }
                    property?.getter?.call(this)?.let { collection ->
                        processCollection(collection)
                    }
                }
                // Если параметр data-класс — рекурсивный вызов
                else -> {
                    kClass.memberProperties.find { it.name == param.name }
                        ?.getter?.call(this)?.let { value ->
                            if (value::class.isData) {
                                value.setAllNullableFieldsToNull()
                            } else {
                                value
                            }
                        }
                }
            }

            any

//            if (param.type.isMarkedNullable) {
//                null
//            } else {
//                kClass
//                    .memberProperties
//                    .find { it.name == param.name }
//                    ?.let { property ->
//                        property
//                            .getter
//                            .call(this)
//                            ?.let { data ->
//                                if (data::class.isData) {
//                                    data.setAllNullableFieldsToNull()
//                                } else {
//                                    data
//                                }
//                            }
//                    }
//            }
        }
    return constructor.callBy(args)
}

/**
 * Обрабатывает коллекции (List/Set/Map), рекурсивно применяя setAllNullableFieldsToNull() к элементам.
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
 * Обрабатывает значение: если оно data-класс — рекурсивно обнуляет nullable-поля.
 */
private fun Any.processNullableValue(): Any? {
    return if (this::class.isData) {
        this.setAllNullableFieldsToNull()
    } else {
        this
    }
}

/**
 * Проверяет, является ли тип коллекцией (List/Set/Map).
 */
private fun KType.isCollectionOrMap(): Boolean {
    return when (val classifier = this.classifier) {
        is KClass<*> -> classifier.isSubclassOf(List::class) ||
                classifier.isSubclassOf(Set::class) ||
                classifier.isSubclassOf(Map::class)
        else -> false
    }
}
