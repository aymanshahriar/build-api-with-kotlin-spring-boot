package tv.codealong.tutorials.springboot.thenewboston.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import tv.codealong.tutorials.springboot.thenewboston.datasource.BankDataSource
import tv.codealong.tutorials.springboot.thenewboston.model.Bank

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk(relaxed = true) // relaxed means that whenever a method is called on it, it will return some kind of default value
    private val bankService = BankService(dataSource)
    private val banks = mutableListOf(
        Bank("1111", 1.0, 1),
        Bank("2222", 2.0, 2),
        Bank("3333", 3.0, 3)
    )

    @Test
    fun `should call its data source to retrieve banks`() {
        // given
        every {dataSource.retrieveBanks()} returns banks

        // when
        var result = bankService.getBanks()

        // then
        verify(exactly = 1) { dataSource.retrieveBanks() }
        assertEquals(banks, result)
    }
}