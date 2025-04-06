package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.enumR.AbstractEnumRouter
import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorker

@Service
class OtherEnumRouter(
    iWorkerList: List<IEnumWorker<INEnumRouterData, OutEnumRouterData, OtherEnum>>
) : AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, OtherEnum>(
    iWorkerList,
    OtherEnum.entries
)