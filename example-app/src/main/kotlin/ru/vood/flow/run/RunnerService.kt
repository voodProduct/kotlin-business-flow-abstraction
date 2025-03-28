package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
//import ru.vood.flow.abstraction.router.AbstractWebRouter
import ru.vood.flow.abstraction.router.enumR.AbstractEnumRouter
//import ru.vood.flow.abstraction.router.mapper.simple.AbstractMapperRouter
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.EitherMapperRouter
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.IValidateMapperError
import ru.vood.flow.config.enumR.INEnumRouterData
import ru.vood.flow.config.enumR.OutEnumRouterData
import ru.vood.flow.config.enumR.SomeEnum
import ru.vood.flow.eitherMapper.IDateMapper
import ru.vood.flow.eitherMapper.IIntMapper
import java.time.Instant

@Service
class RunnerService(
//    val webRouter: AbstractWebRouter,
//    val mpperRouter: AbstractMapperRouter,
    val eitherMapperRouter : EitherMapperRouter<Any, Any, IValidateMapperError>,
    val someEnumRouter: AbstractEnumRouter<INEnumRouterData, OutEnumRouterData, SomeEnum>,


    ): CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {


//            webRouter.routedMap.map {
//                val some = Arranger.some(it.key.tkClass.java)
//                val run = it.value.run { some }
//                println("webRouter -> "+run)
//            }
//
//            val run = webRouter.run<Int, String> { 1 }
//            println(run)
//
//            val run1 = webRouter.run<Instant, String> { Instant.now() }
//            println(run1)
//
//            val run2 = mpperRouter.mapData<Int, String> { 1 }
//            println(run2)
//
//            val run3 = mpperRouter.mapData<Instant, String> { Instant.now() }
//            println(run3)

            val run4 = eitherMapperRouter.mapData<Int, String, IIntMapper> { 1 }
            println(run4)

            val run5 = eitherMapperRouter.mapData<Instant, String, IDateMapper> { Instant.now() }
            println(run5)

            val mapData = someEnumRouter.mapData<INEnumRouterData, OutEnumRouterData>(
                { Arranger.some(INEnumRouterData::class.java) },
                { SomeEnum.Q })
            println("someEnumRouter -> "+mapData)
        }
    }
}