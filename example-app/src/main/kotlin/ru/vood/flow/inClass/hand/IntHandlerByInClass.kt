package ru.vood.flow.inClass.hand

import org.springframework.stereotype.Service
import ru.vood.flow.inClass.HandlerByInClassId
import ru.vood.flow.inClass.IHandlerByInClass
import ru.vood.flow.inClass.dto.IntWrapped

@Service
class IntHandlerByInClass : IHandlerByInClass<IntWrapped> {

    override val workerId: HandlerByInClassId<IntWrapped>
        get() = HandlerByInClassId(IntWrapped::class)

    override suspend fun doWork(data: IntWrapped): String {
        return data.int.toString()
    }


}