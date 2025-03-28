package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.enumR.EnumWorkerId
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.OtherEnum
import ru.vood.flow.enumR.OutEnumRouterData
import ru.vood.flow.enumR.SomeEnum

@Service
class OtherEnumWorker: IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<OtherEnum>> {

    override val workerId: EnumWorkerId<OtherEnum>
        get() = EnumWorkerId(OtherEnum.Q)

    override suspend fun doWork(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }
}
