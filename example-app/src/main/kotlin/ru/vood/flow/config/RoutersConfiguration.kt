package ru.vood.flow.config

//import ru.vood.flow.abstraction.router.AbstractWebRouter
//import ru.vood.flow.abstraction.router.mapper.simple.AbstractMapperRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vood.flow.abstraction.router.abstraction.IWorker
import ru.vood.flow.abstraction.router.enumR.AbstractEnumRouter
import ru.vood.flow.abstraction.router.enumR.EnumWorkerId
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.OutEnumRouterData
import ru.vood.flow.enumR.SomeEnum

@Configuration
class RoutersConfiguration {



    @Bean
    fun abstractEnumRouterBean(handlers: List<IWorker<INEnumRouterData, OutEnumRouterData, EnumWorkerId<SomeEnum>>>): AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, SomeEnum> {
        val value =
            object : AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, SomeEnum>(handlers, SomeEnum.entries) {}
        return value
    }


}