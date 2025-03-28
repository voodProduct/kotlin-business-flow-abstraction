package ru.vood.flow.config.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.enumR.AbstractEnumRouter
import ru.vood.flow.abstraction.router.enumR.EnumWorkerId

@Service
class OtherEnumRouter(
    iWorkerList: List<IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<OtherEnum>>>) : AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, OtherEnum>(
    iWorkerList,
    OtherEnum.entries
) {
}