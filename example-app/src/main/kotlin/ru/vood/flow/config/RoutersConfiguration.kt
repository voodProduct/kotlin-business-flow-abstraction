package ru.vood.flow.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.vood.flow.abstraction.router.IHandler
import ru.vood.flow.abstraction.router.WebRouter
import ru.vood.flow.abstraction.router.mapper.IMapper
import ru.vood.flow.abstraction.router.mapper.MapperRouter

@Configuration
class RoutersConfiguration {

    @Bean
    fun asdasdasd(handlers: List<IHandler<*, *>>): WebRouter {
        val map = handlers.map { it as IHandler<Any, Any> }
        return WebRouter(map)
    }


    @Bean
    fun mapperRouterBean(handlers: List<IMapper<*, *>>): MapperRouter = MapperRouter(handlers.map { it as IMapper<Any, Any> })

}