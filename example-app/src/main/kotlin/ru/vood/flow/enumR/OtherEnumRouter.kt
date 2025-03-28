package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.abstraction.enumR.AbstractEnumRouter
import ru.vood.flow.abstraction.router.abstraction.enumR.EnumWorkerId

@Service
class OtherEnumRouter(
    iWorkerList: List<IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<OtherEnum>>>) : AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, OtherEnum>(
    iWorkerList,
    OtherEnum.entries
) {
}