package ru.vood.flow.mapper

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.mapper.simple.IMapper
import ru.vood.flow.abstraction.router.mapper.simple.MapperId

@Service
class StringIMapper : IMapper<Int, String> {
    override fun handle(data: Int): String = data.toString()

    override val workerId: MapperId<Int, String>
        get() = MapperId(Int::class, String::class)


}