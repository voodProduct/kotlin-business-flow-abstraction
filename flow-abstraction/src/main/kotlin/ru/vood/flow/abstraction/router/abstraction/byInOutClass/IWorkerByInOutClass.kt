package ru.vood.flow.abstraction.router.abstraction.byInOutClass

import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IWorkerByInOutClass<
        T : Any,
        out R : Any,
         WORKER_ID : IWorkerIdByInOutClass<T, R>
        > : IWorker<T, R, WORKER_ID>