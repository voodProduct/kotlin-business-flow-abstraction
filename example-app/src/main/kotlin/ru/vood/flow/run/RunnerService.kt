package ru.vood.flow.run

import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.WebRouter
import ru.vood.flow.abstraction.router.mapper.MapperRouter
import java.time.Instant

@Service
class RunnerService(
    val webRouter: WebRouter,
    val mpperRouter: MapperRouter,

    ): CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val run = webRouter.run<Int, String> { 1 }
            println(run)

            val run1 = webRouter.run<Instant, String> { Instant.now() }
            println(run1)

            val run2 = mpperRouter.mapData<Int, String> { 1 }
            println(run2)

            val run3 = mpperRouter.mapData<Instant, String> { Instant.now() }
            println(run3)


            mpperRouter
        }


    }
}