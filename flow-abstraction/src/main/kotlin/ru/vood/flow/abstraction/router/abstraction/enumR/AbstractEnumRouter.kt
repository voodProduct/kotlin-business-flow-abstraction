package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.IRouter
import kotlin.enums.EnumEntries

abstract class AbstractEnumRouter<T : Any, R : Any, E>
    (
    iWorkerList: List<IEnumWorker<T, R, E>>,
    eVals: EnumEntries<E>,
) : IRouter<
        T,
        R,
        E,
        IEnumWorker<T, R, E>
        >
        where E : Enum<E>
 {

    init {
        val workerIEnumWorker = iWorkerList.flatMap { it.workerId }.map { it.name }.toSet()
        val filter = eVals.filter { !workerIEnumWorker.contains(it.name) }
        require(filter.isEmpty()) {
            """Fot router ${this::class.java.canonicalName} 
            |           Not found worker implementation ${IEnumWorker::class.java.canonicalName} 
            |           for next EnumValues $filter of Enum type ${eVals.first()::class.java.canonicalName}""".trimMargin()
        }
    }



    suspend inline fun <reified IT : T, reified IR : R> mapData(
        crossinline data: suspend () -> IT,
        enumF: () -> E
    ): R {
        val emun = enumF()
        return data().let { dataDto ->
            routedMap[emun]?.doWork(dataDto, emun)
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $emun")
    }
}