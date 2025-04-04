package ru.vood.flow.inClass.hand

import org.springframework.stereotype.Service
import ru.vood.flow.inClass.HandlerByInClassId
import ru.vood.flow.inClass.IHandlerByInClass
import ru.vood.flow.inClass.dto.InstantWrapped

@Service
class DateHandlerByInClass : IHandlerByInClass<InstantWrapped> {

    override val workerId: HandlerByInClassId<InstantWrapped>
        get() = HandlerByInClassId(InstantWrapped::class)

    override suspend fun doWork(data: InstantWrapped): String {
        return data.instant.toString()
    }


}