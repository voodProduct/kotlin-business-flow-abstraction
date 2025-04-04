package ru.vood.flow.inClass

import org.springframework.stereotype.Service
import ru.vood.flow.abstraction.router.abstraction.byInClass.AbstractRouterByInClass
import ru.vood.flow.inClass.dto.IInputDto

@Service
class RouterByInClass(
    iWorkerList: List<IHandlerByInClass<*>>
) : AbstractRouterByInClass<IInputDto, String, HandlerByInClassId<IInputDto>, IHandlerByInClass<IInputDto>>(
    iWorkerList.map { it as  IHandlerByInClass<IInputDto> }
) {

    final suspend inline fun <reified IT : IInputDto> processInput(
        crossinline data: suspend () -> IT,
    ): String {
        return mapData<IT, String>(data, { HandlerByInClassId(IT::class) })
    }

}