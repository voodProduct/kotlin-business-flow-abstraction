package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.enumR.AbstractEnumRouter
import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorker

@Service
class SomeEnumRouter(
    iWorkerList: List<IEnumWorker<INEnumRouterData, OutEnumRouterData, SomeEnum>>
) : AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, SomeEnum>(
    iWorkerList,
    SomeEnum.entries
)