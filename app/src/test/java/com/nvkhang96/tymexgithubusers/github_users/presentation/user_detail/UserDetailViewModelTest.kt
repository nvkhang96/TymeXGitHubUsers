package com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail

import app.cash.turbine.test
import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.domain.use_cases.GetUserDetailUseCase
import com.nvkhang96.tymexgithubusers.github_users.presentation.utils.PresentationMockUtil
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserDetailViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private lateinit var getUserDetailUseCase: GetUserDetailUseCase
    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
        getUserDetailUseCase = mockk()
        viewModel = UserDetailViewModel(getUserDetailUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onIntent GetUser should update state with user detail on success`() = runTest {

        val expectedUser = PresentationMockUtil.users.first()
        val username = expectedUser.username
        coEvery {
            getUserDetailUseCase(GetUserDetailUseCase.Input(username))
        } returns flowOf(DataResult.Success(expectedUser))

        viewModel.state.test {

            assertEquals(UserDetailViewModel.State(), awaitItem())

            viewModel.onIntent(UserDetailViewModel.Intent.GetUser(username))

            assertEquals(
                UserDetailViewModel.State(user = expectedUser, error = null),
                awaitItem(),
            )
        }
        coVerify { getUserDetailUseCase(GetUserDetailUseCase.Input(username)) }
    }

    @Test
    fun `onIntent GetUser should update state with error on failure`() = runTest {

        val username = PresentationMockUtil.users.first().username
        val expectedError = "Network error"
        coEvery {
            getUserDetailUseCase(GetUserDetailUseCase.Input(username))
        } returns flowOf(
            DataResult.Error(exception = Exception(expectedError))
        )

        viewModel.state.test {

            assertEquals(UserDetailViewModel.State(), awaitItem())

            viewModel.onIntent(UserDetailViewModel.Intent.GetUser(username))

            assertEquals(expectedError, awaitItem().error)
        }
        coVerify { getUserDetailUseCase(GetUserDetailUseCase.Input(username)) }
    }
}