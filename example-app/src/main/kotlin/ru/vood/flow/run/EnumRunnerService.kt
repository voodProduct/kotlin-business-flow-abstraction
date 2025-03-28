package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
//import ru.vood.flow.abstraction.router.AbstractWebRouter
//import ru.vood.flow.abstraction.router.mapper.simple.AbstractMapperRouter
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.OutEnumRouterData
import ru.vood.flow.enumR.SomeEnum
import ru.vood.flow.enumR.SomeEnumRouter

@Service
class EnumRunnerService(
    val router: SomeEnumRouter,
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val mapData = router.mapData<INEnumRouterData, OutEnumRouterData>(
                { Arranger.some(INEnumRouterData::class.java) },
                { SomeEnum.Q })
            println("${router::class.java.canonicalName} -> " + mapData)
        }
    }
}