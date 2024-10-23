package com.nvkhang96.tymexgithubusers.github_users.domain.use_cases

import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import com.nvkhang96.tymexgithubusers.github_users.domain.utils.DomainMockUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GetUserDetailUseCaseTest {

    private lateinit var mockRepository: UserRepository
    private lateinit var getUserDetailUseCase: GetUserDetailUseCase

    @Before
    fun setup() {
        mockRepository = mockk()
        getUserDetailUseCase = GetUserDetailUseCase(mockRepository)
    }

    @Test
    fun `invoke should return user detail on success`() = runTest {
        val expectation = DomainMockUtil.users.first()
        val input = GetUserDetailUseCase.Input(username = expectation.username)
        coEvery {
            mockRepository.getUserDetail(input.username)
        } returns flowOf(
            DataResult.Success(expectation)
        )

        val actual = getUserDetailUseCase(input).first()

        assertEquals(DataResult.Success(expectation), actual)
        coVerify { mockRepository.getUserDetail(input.username) }
    }

    @Test
    fun `invoke should return error on failure`() = runTest {

        val input = GetUserDetailUseCase.Input(username = "")
        val exception = DomainMockUtil.exception
        coEvery {
            mockRepository.getUserDetail(input.username)
        } returns flowOf(DataResult.Error(exception = exception))

        val actual = getUserDetailUseCase(input).first()

        assertEquals(DataResult.Error(exception = exception), actual)
        coVerify { mockRepository.getUserDetail(input.username) }
    }
}