package ru.vood.kotlin.test.mapper

import io.kotest.datatest.WithDataTestName

sealed interface ITestCase<T : Any, R : Any> : WithDataTestName {
    /**словесное описание тест кейза, будет выведено при прогоне*/
    val description: String

    /**входная ДТО для маппера*/
    val testCaseTestData: T

    /**набор моков, если нужен */
    val mockks: () -> Any?
    override fun dataTestName(): String = description
}

data class ErrorTestCaseData<T : Any, R : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: T,
    /**если планируется что при маппинге должено быть исключение, то тут должен быть записан его текст,
     * заполняется, то лько если extractFun и expectedVal пустые
     * */
    val error: String,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {},
) : ITestCase<T, R>

data class OkTestCaseData<T : Any, R : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: T,
    /**ф-ция, которая принимает ДТО с выхода маппера и может вытащить конкретный атрибут.*/
    val extractFun: (R) -> Any? = { error("override extractFun") },
    /**ожидаемый результат, будет сравнен с тем что вернула extractFun */
    val expectedVal: Any? = null,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {},
) : ITestCase<T, R>


/** тесткейз */
data class TestCaseData<T : Any, R : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: T,
    /**если планируется что при маппинге должено быть исключение, то тут должен быть записан его текст,
     * заполняется, то лько если extractFun и expectedVal пустые
     * */
    val error: String? = null,
    /**ф-ция, которая принимает ДТО с выхода маппера и может вытащить конкретный атрибут.*/
    val extractFun: (R) -> Any? = { error("override extractFun") },
    /**ожидаемый результат, будет сравнен с тем что вернула extractFun */
    val expectedVal: Any? = null,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {}
) : WithDataTestName, ITestCase<T, R> {
    init {
        val notErrorFl = error == null
        val errorFl = error != null && expectedVal == null
        require(notErrorFl || errorFl) {
            """for testcase '${description}' must one of: 
                |(error == null)=true or 
                |(error != null && expectedVal == null)=true
                |but:
                |(error == null) = $notErrorFl
                |(error != null && expectedVal == null) = $errorFl
                |""".trimMargin()
        }
    }

    override fun dataTestName(): String = description
}