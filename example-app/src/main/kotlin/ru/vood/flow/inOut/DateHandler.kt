package ru.vood.flow.inOut

import org.springframework.stereotype.Service

import java.time.Instant

@Service
class DateHandler : IHandler<Instant, String> {

    override val workerId: HandlerId<Instant, String>
        get() = HandlerId(Instant::class, String::class)

    override suspend fun doWork(data: Instant): String {
        return data.toString()
    }


}