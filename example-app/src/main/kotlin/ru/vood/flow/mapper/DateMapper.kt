package ru.vood.flow.mapper

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.IMapper
import ru.vood.flow.abstraction.router.mapper.MapperId
import java.time.Instant

@Service
class DateMapper : IMapper<Instant, String> {

    override val workerId: MapperId<Instant, String>
        get() = MapperId(Instant::class, String::class)

    override fun handle(data: Instant): String = data.toString()


}