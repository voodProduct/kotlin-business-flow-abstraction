package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.abstraction.enumR.EnumWorkerId

@Service
class EnumWorker : IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<SomeEnum>> {

    override val workerId: EnumWorkerId<SomeEnum>
        get() = EnumWorkerId(SomeEnum.Q)

    override suspend fun doWork(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }
}
