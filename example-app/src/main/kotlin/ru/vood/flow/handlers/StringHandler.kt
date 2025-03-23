package ru.vood.flow.handlers

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.HandlerId
import ru.vood.flow.abstraction.router.IHandler

@Service
class StringHandler : IHandler<Int, String> {
    override fun handle(data: Int): String {
        return data.toString()
    }

    override val workerId: HandlerId<Int, String>
        get() = HandlerId(Int::class, String::class)


}