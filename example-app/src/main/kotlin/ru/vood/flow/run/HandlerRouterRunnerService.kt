package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.inOut.HandlerRouter
import java.time.Instant

@Service
class HandlerRouterRunnerService(
    val router: HandlerRouter,
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val mapData = router.handle<Instant, String> { Arranger.some(Instant::class.java) }
            println("${router::class.java.canonicalName} -> " + mapData)

            val mapData1 = router.handle<Int, String> { Arranger.some(Int::class.java) }
            println("${router::class.java.canonicalName} -> " + mapData1)
        }
    }
}