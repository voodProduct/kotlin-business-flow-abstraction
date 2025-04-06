package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.enumR.IEnumWorker

@Service
class WorkerOtherEnum : IEnumWorker<INEnumRouterData, OutEnumRouterData, OtherEnum> {

    override fun handle(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }

    override val workerId: OtherEnum
        get() = OtherEnum.OtherEnum_Q

}
