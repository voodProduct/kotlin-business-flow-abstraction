package ru.vood.kotlin.test.mapper

import com.ocadotechnology.gembus.test.Arranger
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerByInOutClass
import ru.vood.flow.abstraction.router.abstraction.byInOutClass.IWorkerIdByInOutClass

/** Абстракция для тестирования типовых мапперов
 *  T - класс из которого мапятся данные
 *  R - класс, в который будут записаны данные
 * */
abstract class AbstractInOutClassTypicalMapperTest<T : Any, R : Any, WORKER_ID : IWorkerIdByInOutClass<T, R>> :
    FunSpec() {


    /** экземпляр маппера, который сейчас тестируется */
    abstract val iMapper: IWorkerByInOutClass<T, R, WORKER_ID>

    /** экземпляр маппера, который сейчас тестируется */
    abstract val workerId: WORKER_ID

    /**перечень тест кейзов */
    abstract val testCases: List<ITestCase<T, R>/*TestCaseData<T, R>*/>

    /**генерирует экземпляр ДТО с тестовыми данными, запоминает первый экземпляр */
    val testDataEtalon: T by lazy {
        generateTestDataEtalon()
    }

    /** генерирует экземпляр ДТО с тестовыми данными, при каждом запуске генерируется новый ДТО
     * */
    protected open fun generateTestDataEtalon(): T = Arranger.some(workerId.tkClass.java)

    init {
        withData(this.testCases) {
            with(it) {
                mockks()
                when (this) {
                    is ErrorTestCaseData<T, R> -> {
                        val shouldThrow = shouldThrow<IllegalStateException> {
                            iMapper.doWork(testCaseTestData, workerId)
                        }
                        shouldThrow.message shouldBe error
                    }
                    is OkTestCaseData<T, R> -> {
                        val mapData: R = iMapper.doWork(testCaseTestData, workerId)
                        val extractedVal: Any? = extractFun(mapData)
                        extractedVal shouldBe expectedVal
                    }
                }
            }
        }
    }
}