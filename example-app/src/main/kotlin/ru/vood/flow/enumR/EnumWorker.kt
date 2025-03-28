package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.enumR.EnumWorkerId
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.OutEnumRouterData
import ru.vood.flow.enumR.SomeEnum

@Service
class EnumWorker: IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<SomeEnum>> {

    override val workerId: EnumWorkerId<SomeEnum>
        get() = EnumWorkerId(SomeEnum.Q)

    override suspend fun doWork(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }
}
