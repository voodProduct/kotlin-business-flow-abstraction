package ru.vood.flow.inOut

import org.springframework.stereotype.Service

@Service
class IntHandler : IHandler<Int, String> {
    override suspend fun doWork(data: Int): String {
        return data.toString()
    }

    override val workerId: HandlerId<Int, String>
        get() = HandlerId(Int::class, String::class)


}