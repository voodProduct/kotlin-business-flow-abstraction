package ru.vood.flow.abstraction.router.abstraction.enumR

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter
import kotlin.enums.EnumEntries

abstract class AbstractEnumRouter<T : Any, R : Any, E>
    (
//    iWorkerList: List<IWorker<T, R, EnumWorkerId<E>>>,
//    iWorkerList: List<IWorker<T, R, IEnumWorkerId<E>>>,
    iWorkerList: List<IEnumWorker<T, R, E>>,
    eVals: EnumEntries<E>,
) : AbstractRouter<
        T,
        R,
        E,
        IEnumWorker<T, R, E>
        >(iWorkerList)
        where E : Enum<E>,
              E : IEnumWorkerId<out E> {

    init {
        val workerIEnumWorker = iWorkerList.map { it.workerId.emun() }.toSet()
        val filter = eVals.filter { !workerIEnumWorker.contains(it) }
        require(filter.isEmpty()) { """Fot router ${this::class.java.canonicalName} 
            |           Not found worker implementation ${IEnumWorker::class.java.canonicalName} 
            |           for next EnumValues $filter of Enum type ${eVals.first()::class.java.canonicalName}""".trimMargin() }
    }

    suspend inline fun <reified IT : T, reified IR : R> mapData(
        crossinline data: suspend () -> IT,
        enumF: () -> E
    ): R {
        val emun = enumF()
//        val validateMapperId = EnumWorkerId(emun)
        return data().let { dataDto ->
            routedMap[emun]?.doWork(dataDto)
        } ?: error("For router ${this::class.java.canonicalName} not found worker with Id $emun")
    }
}