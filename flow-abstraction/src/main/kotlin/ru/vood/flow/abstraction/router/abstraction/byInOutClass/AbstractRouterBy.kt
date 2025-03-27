package ru.vood.flow.abstraction.router.abstraction.byInOutClass

import ru.vood.flow.abstraction.router.abstraction.AbstractRouter

abstract class AbstractRouterBy<wId : IWorkerIdByInOutClass<Any, Any>, worker : IWorkerByInOutClass<Any, Any, wId>>(
    iWorkerList: List<worker>
) : AbstractRouter<wId, worker>(iWorkerList)