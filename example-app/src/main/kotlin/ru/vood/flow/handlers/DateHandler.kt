package ru.vood.flow.handlers

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.HandlerId
import ru.vood.flow.abstraction.router.IHandler
import java.time.Instant

@Service
class DateHandler : IHandler<Instant, String> {
    override fun handle(data: Instant): String {
        return data.toString()
    }

    override val workerId: HandlerId<Instant, String>
        get() = HandlerId(Instant::class, String::class)


}