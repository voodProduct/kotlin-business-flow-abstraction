package ru.vood.flow.abstraction.router.abstraction.byInClass

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IWorkerByInClass<
        T : Any,
        out R : Any,
        out WORKER_ID : IWorkerIdByInClass<T>> : IWorker<T, R, WORKER_ID>