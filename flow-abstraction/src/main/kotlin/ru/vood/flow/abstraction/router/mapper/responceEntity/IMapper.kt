package ru.vood.flow.abstraction.router.mapper.responceEntity

import org.springframework.http.ResponseEntity
import ru.vood.flow.abstraction.router.abstraction.IWorker

interface IResponseEntityMapper<TT : Any> :
    IWorker<TT, ResponseEntity<String>, IResponseEntityWorkerId<TT>> {
    override suspend fun doWork(data: TT): ResponseEntity<String> {
        return handle(data)
    }

    //    override suspend fun <T, R> doWork(data: T): R {
//        print(this::class.java.canonicalName+" -> ")
//        return handle(data as TT) as R
//    }

    fun handle(data: TT): ResponseEntity<String>


}