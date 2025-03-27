package ru.vood.flow.abstraction.router.abstraction.byInOutClass

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IWorkerByInOutClass<out T: Any, out R: Any, out WORKER_ID: IWorkerIdByInOutClass<T, R>>: IWorker<WORKER_ID>