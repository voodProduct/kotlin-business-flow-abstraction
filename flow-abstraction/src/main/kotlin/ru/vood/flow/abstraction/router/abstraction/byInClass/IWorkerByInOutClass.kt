package ru.vood.flow.abstraction.router.abstraction.byInClass

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IWorkerByInOutClass<
        T : Any,
        out R : Any,
        out WORKER_ID : IWorkerIdByInOutClass<T, R>> : IWorker<T, R, WORKER_ID>