package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.enumR.EnumWorkerId
import ru.vood.flow.config.enumR.INEnumRouterData
import ru.vood.flow.config.enumR.OtherEnum
import ru.vood.flow.config.enumR.OutEnumRouterData
import ru.vood.flow.config.enumR.SomeEnum

@Service
class OtherEnumWorker: IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<OtherEnum>> {

    override val workerId: EnumWorkerId<OtherEnum>
        get() = EnumWorkerId(OtherEnum.Q)

    override suspend fun doWork(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }
}
