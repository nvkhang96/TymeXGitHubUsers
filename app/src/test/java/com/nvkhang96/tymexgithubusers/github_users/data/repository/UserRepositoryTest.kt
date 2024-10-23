package com.nvkhang96.tymexgithubusers.github_users.data.repository

import app.cash.turbine.test
import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.data.local.GitHubUserDatabase
import com.nvkhang96.tymexgithubusers.github_users.data.local.dao.UserDao
import com.nvkhang96.tymexgithubusers.github_users.data.local.entities.UserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.mappers.toUserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.mappers.toUserModel
import com.nvkhang96.tymexgithubusers.github_users.data.remote.GitHubApi
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_detail.UserDetailDto
import com.nvkhang96.tymexgithubusers.github_users.data.repositories.UserRepositoryImpl
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryTest {

    private val dispatcher = UnconfinedTestDispatcher()

    private val api = mockk<GitHubApi>()
    private val database = mockk<GitHubUserDatabase>()
    private val userDao = mockk<UserDao>()
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        repository = UserRepositoryImpl(api, database, userDao, dispatcher)
    }

    @Test
    fun `getUserDetail should return cached user and then update with remote data`() = runTest {

        val username = "username"
        val cachedUserEntity = UserEntity(username = username, location = "Cached User")
        val cachedUser = cachedUserEntity.toUserModel()
        val remoteUserDetailDto = UserDetailDto(login = username, location = "Remote User")
        val remoteUserEntity = remoteUserDetailDto.toUserEntity()
        val remoteUser = remoteUserEntity.toUserModel()
        coEvery { userDao.getUserByUsername(username) } returns cachedUserEntity
        coEvery { api.getUserDetail(username) } returns remoteUserDetailDto
        coEvery { userDao.update(any()) } returns Unit

        repository.getUserDetail(username).test {
            assertEquals(DataResult.Success(cachedUser), awaitItem())
            assertEquals(DataResult.Success(remoteUser), awaitItem())
            awaitComplete()
        }
        coVerify { api.getUserDetail(username) }
        coVerify { userDao.update(any()) }
    }

    @Test
    fun `getUserDetail should return cached user and then error when API call fails`() = runTest {

        val username = "username"
        val cachedUserEntity = UserEntity(username = username, location = "Cached User")
        val cachedUser = cachedUserEntity.toUserModel()
        val expectedError = Exception("Network error")
        coEvery { userDao.getUserByUsername(username) } returns cachedUserEntity
        coEvery { api.getUserDetail(username) } throws expectedError

        repository.getUserDetail(username).test {
            assertEquals(DataResult.Success(cachedUser), awaitItem())
            assertEquals(DataResult.Error(exception = expectedError), awaitItem())
            awaitComplete()
        }
        coVerify(inverse = true) { userDao.update(any()) }
    }
}