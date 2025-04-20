package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.enumR.INEnumRouterData
import ru.vood.flow.enumR.other.OtherEnum
//import ru.vood.flow.enumR.other.OtherEnumRouter
import ru.vood.flow.enumR.OutEnumRouterData

//@Service
//class OtherRunnerService(
//    val router: OtherEnumRouter,
//) : CommandLineRunner {
//    override fun run(vararg args: String?) {
//        runBlocking {
//            val mapData = router.mapData<INEnumRouterData, OutEnumRouterData>(
//                { Arranger.some(INEnumRouterData::class.java) },
//                { OtherEnum.OtherEnum_Q })
//            println("${router::class.java.canonicalName} -> " + mapData)
//        }
//    }
//}