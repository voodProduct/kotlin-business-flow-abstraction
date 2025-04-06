package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorker

@Service
class WorkerSomeEnum : IEnumWorker<INEnumRouterData, OutEnumRouterData, SomeEnum> {
    override fun handle(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }

    override val workerId: SomeEnum
        get() = SomeEnum.SomeEnum_Q

}
