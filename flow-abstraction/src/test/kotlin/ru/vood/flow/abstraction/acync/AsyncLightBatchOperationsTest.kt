package ru.vood.flow.abstraction.acync

import arrow.core.Either
import kotlinx.coroutines.delay
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.vood.flow.abstraction.acync.AsyncLightBatchOperations.asyncRunBlockingBatch
import ru.vood.flow.abstraction.acync.AsyncLightBatchOperations.withFunction
import java.util.concurrent.atomic.AtomicInteger

class AsyncBatchOperationsTest {
    val LOGGER: Logger = LoggerFactory.getLogger(AsyncBatchOperationsTest::class.java)

    @Test
    fun testAsyncOk() {
        val batchData = (1..10).map { it.toString() }.toList()
        val units = asyncRunBlockingBatch { batchData } withFunction { qw -> qw.toInt() to Thread.currentThread().name }

        assertEquals(batchData.map { it.toInt() }, units.map { it.first })

        val toMap = units.toMap().values.distinct()
        assertTrue(toMap.size > 1)
    }

    @Test
    fun testAsyncErr() {
        val batchData = (1..10).map { it.toString() }.toList().plus("err")
        val assertThrows =
            assertThrows<NumberFormatException> {
                asyncRunBlockingBatch { batchData } withFunction { qw ->
                    Thread.currentThread().name to qw.toInt()
                }
            }
        assertEquals("""For input string: "err"""", assertThrows.message)
    }

    @Test
    fun testBatchAsyncOk() {
        val batchData = (1..10).map { it.toString() }.toList().plus("err")
        val applyBatchOfValues =
            AsyncBatchOperations<String, Int, Map<String, Either<Throwable, Int>>>(
                doOnFail = { inVal, err -> LOGGER.debug("при обработке ${inVal} выскочила ошибка ${err.message}") },
                doOnSuccess = { inVal, outVal -> LOGGER.debug("при обработке ${inVal} получили ${outVal}") },
                resultCombiner = { qw: Map<String, Either<Throwable, Int>> -> qw }
            ).applyBatchOfValues(
                batch = batchData.map { AsyncValue(it) },
                reprocessCondition = AsyncBatchOperations.DEFAULT_REPROCESS_CONDITION
            ) { it.toInt() }


        val filterIsRight = applyBatchOfValues.values.filter { it.isRight() }
        val filterIsLeft = applyBatchOfValues.values.filter { it.isLeft() }
        assertEquals(10, filterIsRight.size)
        assertEquals(1, filterIsLeft.size)
    }

    @Test
    fun testBatchAsyncCommonTimeOk() {
        val batchData = (1..10).map { it.toString() }.toList().plus("err")
        val applyBatchOfValues =
            AsyncBatchOperations<String, Int, Map<String, Either<Throwable, Int>>>(
                doOnFail = { inVal, err -> LOGGER.debug("""при обработке ${inVal} выскочила ошибка "${err.message}""") },
                doOnSuccess = { inVal, outVal -> LOGGER.debug("при обработке ${inVal} получили ${outVal}") },
                resultCombiner = { qw: Map<String, Either<Throwable, Int>> -> qw }
            ).applyBatchOfValues(
                batch = batchData,
                timeout = 1000,
                reprocessAttempts = 1,
                reprocessCondition = AsyncBatchOperations.DEFAULT_REPROCESS_CONDITION
            ) { it.toInt() }


        val filterIsRight = applyBatchOfValues.values.filter { it.isRight() }
        val filterIsLeft = applyBatchOfValues.values.filter { it.isLeft() }
        assertEquals(10, filterIsRight.size)
        assertEquals(1, filterIsLeft.size)
    }

    @Test
    fun testBatchAsyncCommonTimeReprocess() {
        val cntReprocess = AtomicInteger(0)
        val batchData = listOf("err")
        val applyBatchOfValues =
            AsyncBatchOperations<String, Int, Map<String, Either<Throwable, Int>>>(
                doOnFail = { inVal, err -> LOGGER.debug("""при обработке ${inVal} выскочила ошибка "${err.message}""") },
                doOnSuccess = { inVal, outVal -> LOGGER.debug("при обработке ${inVal} получили ${outVal}") },
                resultCombiner = { qw: Map<String, Either<Throwable, Int>> -> qw }
            ).applyBatchOfValues(
                batch = batchData,
                timeout = 1000,
                reprocessAttempts = 10,
                reprocessCondition = { it is java.lang.NumberFormatException }
            ) {
                cntReprocess.incrementAndGet()
                it.toInt()
            }


        assertEquals(11, cntReprocess.get())
    }

    @Test
//    @Disabled
    fun testBatchAsyncCommonTimeTimeOut() {
        val batchData = listOf("1")
        val applyBatchOfValues =
            AsyncBatchOperations<String, Int, Map<String, Either<Throwable, Int>>>(
                doOnFail = { inVal, err -> LOGGER.debug("""при обработке ${inVal} выскочила ошибка "${err.message}""") },
                doOnSuccess = { inVal, outVal -> LOGGER.debug("при обработке ${inVal} получили ${outVal}") },
                resultCombiner = { qw: Map<String, Either<Throwable, Int>> -> qw }
            ).applyBatchOfValues(
                batch = batchData,
                timeout = 10,
                reprocessAttempts = 0,
                reprocessCondition = AsyncBatchOperations.DEFAULT_REPROCESS_CONDITION
            ) {
                delay(1000)
                it.toInt()
            }

        val filterIsLeft = applyBatchOfValues.values.filter { it.isLeft() }
        assertEquals(1, filterIsLeft.size)
        assertEquals("Timed out waiting for 10 ms", filterIsLeft[0].leftOrNull()!!.message)
    }

}