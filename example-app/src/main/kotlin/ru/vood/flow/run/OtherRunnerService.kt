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
import ru.vood.flow.config.enumR.*
import ru.vood.flow.eitherMapper.IDateMapper
import ru.vood.flow.eitherMapper.IIntMapper
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.OtherEnum
import ru.vood.flow.enumR.OtherEnumRouter
import ru.vood.flow.enumR.OutEnumRouterData
import java.time.Instant

@Service
class OtherRunnerService(
    val router: OtherEnumRouter,
    ): CommandLineRunner {
    override fun run(vararg args: String?) {
        runBlocking {
            val mapData = router.mapData<INEnumRouterData, OutEnumRouterData>(
                { Arranger.some(INEnumRouterData::class.java) },
                { OtherEnum.Q })
            println("${router::class.java.canonicalName} -> "+mapData)
        }
    }
}