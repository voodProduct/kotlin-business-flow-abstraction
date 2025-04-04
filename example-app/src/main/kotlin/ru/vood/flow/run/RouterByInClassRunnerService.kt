package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.eitherMapper.first.InputData
import ru.vood.flow.eitherMapper.first.RestInputData
import ru.vood.flow.eitherMapper.second.IOutRestValidationError
import ru.vood.flow.inClass.RouterByInClass
import ru.vood.flow.inClass.dto.InstantWrapped
import ru.vood.flow.inClass.dto.IntWrapped


@Service
class RouterByInClassRunnerService(
    val router: RouterByInClass,
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val mapData = router
                .processInput<InstantWrapped> {
                    Arranger.some(
                        InstantWrapped::class.java
                    )
                }
            println("${router::class.java.canonicalName} -> " + mapData)

            val mapData1 = router
                .processInput<IntWrapped> {
                    Arranger.some(
                        IntWrapped::class.java
                    )
                }
            println("${router::class.java.canonicalName} -> " + mapData1)
        }
    }
}