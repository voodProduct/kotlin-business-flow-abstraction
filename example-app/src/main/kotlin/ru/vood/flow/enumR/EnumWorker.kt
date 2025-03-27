package ru.vood.flow.enumR

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.enumR.IEnumWorker
import ru.vood.flow.abstraction.router.enumR.OnlyEnumId
import ru.vood.flow.config.INEnumRouterData
import ru.vood.flow.config.OutEnumRouterData
import ru.vood.flow.config.SomeEnum

@Service
class EnumWorker: IEnumWorker<INEnumRouterData, OutEnumRouterData, SomeEnum> {
    override fun handle(data: INEnumRouterData): OutEnumRouterData {
        return OutEnumRouterData(data.i.toString())
    }

    override val workerId: OnlyEnumId<SomeEnum>
        get() = OnlyEnumId(SomeEnum.Q)
}