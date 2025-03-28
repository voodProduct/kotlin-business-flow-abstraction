package ru.vood.flow.run

import com.ocadotechnology.gembus.test.Arranger
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Service
import ru.vood.flow.eitherMapper.first.FirstEitherMapperRouter
import ru.vood.flow.eitherMapper.first.IRestValidationError
import ru.vood.flow.eitherMapper.first.InputData
import ru.vood.flow.eitherMapper.first.RestInputData

@Service
class FirstEitherMapperRouterRunnerService(
    val router: FirstEitherMapperRouter,
) : CommandLineRunner {
    override fun run(vararg args: String?) {

        runBlocking {
            val mapData = router.mapData<RestInputData, InputData, IRestValidationError>(
                { Arranger.some(RestInputData::class.java) },

                )
            println("${router::class.java.canonicalName} -> " + mapData)
        }
    }
}