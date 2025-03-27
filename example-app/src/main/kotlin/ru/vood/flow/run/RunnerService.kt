package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.WebRouter
import ru.vood.flow.abstraction.router.mapper.MapperRouter
import ru.vood.flow.abstraction.router.mapper.mapAndValidate.EitherMapperRouter
import ru.vood.flow.eitherMapper.IDateMapper
import ru.vood.flow.eitherMapper.IStringMapper
import java.time.Instant

@Service
class RunnerService(
    val webRouter: WebRouter,
    val mpperRouter: MapperRouter,
    val eitherMapperRouter: EitherMapperRouter,

    ): CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {


            webRouter.routedMap.map {
                val some = Arranger.some(it.key.tkClass.java)
                val run = it.value.run { some }
                println("webRouter -> "+run)
            }

            val run = webRouter.run<Int, String> { 1 }
            println(run)

            val run1 = webRouter.run<Instant, String> { Instant.now() }
            println(run1)

            val run2 = mpperRouter.mapData<Int, String> { 1 }
            println(run2)

            val run3 = mpperRouter.mapData<Instant, String> { Instant.now() }
            println(run3)

            val run4 = eitherMapperRouter.mapData<Int, String> { 1 }
            println(run4)

            val run5 = eitherMapperRouter.mapData<Instant, String> { Instant.now() }
            println(run5)
        }


    }
}