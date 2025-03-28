package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.eitherMapper.first.InputData
import ru.vood.flow.eitherMapper.first.RestInputData
import ru.vood.flow.eitherMapper.second.IOutRestValidationError
import ru.vood.flow.eitherMapper.second.SecondEitherMapperRouter


@Service
class SecondEitherMapperRouterRunnerService(
    val router: SecondEitherMapperRouter,
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val mapData = router
                .mapData<RestInputData, InputData, IOutRestValidationError> {
                    Arranger.some(
                        RestInputData::class.java
                    )
                }
            println("${router::class.java.canonicalName} -> " + mapData)
        }
    }
}