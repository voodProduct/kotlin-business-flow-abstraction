package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.abstraction.enumR.EnumWorkerId

@Service
class OtherEnumWorker : IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<OtherEnum>> {

    override val workerId: EnumWorkerId<OtherEnum>
        get() = EnumWorkerId(OtherEnum.Q)

    override suspend fun doWork(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }
}
