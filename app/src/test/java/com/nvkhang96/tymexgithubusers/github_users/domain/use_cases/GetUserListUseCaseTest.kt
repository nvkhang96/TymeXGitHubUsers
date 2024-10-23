package com.nvkhang96.tymexgithubusers.github_users.domain.use_cases

import androidx.paging.PagingData
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import com.nvkhang96.tymexgithubusers.github_users.domain.utils.DomainMockUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetUserListUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var getUserListUseCase: GetUserListUseCase

    @Before
    fun setup() {
        mockRepository = mockk()
        getUserListUseCase = GetUserListUseCase(mockRepository)
    }

    @Test
    fun `When success, returns paging data`() = runTest {

        val expectation = PagingData.from(DomainMockUtil.users)
        coEvery {
            mockRepository.getUsersPaging()
        } returns flowOf(expectation)

        val actual = getUserListUseCase().first()

        assertEquals(expectation, actual)
        coVerify { mockRepository.getUsersPaging() }
    }

    @Test
    fun `When fail, throw exception`() = runTest {

        val exception = DomainMockUtil.exception
        coEvery {
            mockRepository.getUsersPaging()
        } returns flow { throw exception }

        runCatching {
            getUserListUseCase().first()
        }.onFailure {
            assertTrue(it is Exception)
            assertEquals(exception, it)
        }
        coVerify { mockRepository.getUsersPaging() }
    }
}