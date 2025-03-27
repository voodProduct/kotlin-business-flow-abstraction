package ru.vood.flow.abstraction.router.enumR

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter
import kotlin.enums.EnumEntries

abstract class AbstractEnumRouter<TT : Any, RR : Any, E : Enum<E>>(
    iWorkerList: List<IEnumWorker<TT, RR, E>>,
    eVals: EnumEntries<E>,
) : AbstractRouter<OnlyEnumId<E>, IEnumWorker<TT, RR, E>>(iWorkerList) {

    init {
        val workerIEnumWorker = iWorkerList.map { it.workerId.emun }.toSet()
        val filter = eVals.filter { !workerIEnumWorker.contains(it) }
        require(filter.isEmpty()){"Not found implementation ${IEnumWorker::class.java.canonicalName} for next EnumValues $filter of Enum type ${eVals.first()::class.java.canonicalName}"}
    }

    suspend inline fun <reified T : TT, reified R : RR> mapData(
        crossinline data: suspend () -> T,
        enumF: () -> E
    ): R =
        route<T, R>(
            data = data,
            workerIdExtractor = {
                OnlyEnumId(enumF())
            })
}