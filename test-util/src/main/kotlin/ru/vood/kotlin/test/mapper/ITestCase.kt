package ru.vood.kotlin.test.mapper

import io.kotest.datatest.WithDataTestName

sealed interface ITestCase<TT : Any> : WithDataTestName {
    /**словесное описание тест кейза, будет выведено при прогоне*/
    val description: String

    /**входная ДТО для маппера*/
    val testCaseTestData: TT

    /**набор моков, если нужен */
    val mockks: () -> Any?
    override fun dataTestName(): String = description
}

data class ErrorTestCaseData<TT : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: TT,
    /**если планируется что при маппинге должено быть исключение, то тут должен быть записан его текст,
     * заполняется, то лько если extractFun и expectedVal пустые
     * */
    val error: String,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {},
) : ITestCase<TT>

data class OkTestCaseData<TT : Any, RR : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: TT,
    /**ф-ция, которая принимает ДТО с выхода маппера и может вытащить конкретный атрибут.*/
    val extractFun: (RR) -> Any? = { error("override extractFun") },
    /**ожидаемый результат, будет сравнен с тем что вернула extractFun */
    val expectedVal: Any? = null,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {},
) : ITestCase<TT>


/** тесткейз */
data class TestCaseData<TT : Any, RR : Any>(
    /**словесное описание тест кейза, будет выведено при прогоне*/
    override val description: String,
    /**входная ДТО для маппера*/
    override val testCaseTestData: TT,
    /**если планируется что при маппинге должено быть исключение, то тут должен быть записан его текст,
     * заполняется, то лько если extractFun и expectedVal пустые
     * */
    val error: String? = null,
    /**ф-ция, которая принимает ДТО с выхода маппера и может вытащить конкретный атрибут.*/
    val extractFun: (RR) -> Any? = { error("override extractFun") },
    /**ожидаемый результат, будет сравнен с тем что вернула extractFun */
    val expectedVal: Any? = null,
    /**набор моков, если нужен */
    override val mockks: () -> Any? = {}
) : WithDataTestName, ITestCase<TT> {
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