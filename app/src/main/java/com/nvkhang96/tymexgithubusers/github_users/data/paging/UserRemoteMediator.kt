package com.nvkhang96.tymexgithubusers.github_users.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nvkhang96.tymexgithubusers.github_users.data.GITHUB_USER_PER_PAGE_DEFAULT_VALUE
import com.nvkhang96.tymexgithubusers.github_users.data.GITHUB_USER_SINCE_DEFAULT_VALUE
import com.nvkhang96.tymexgithubusers.github_users.data.local.GitHubUserDatabase
import com.nvkhang96.tymexgithubusers.github_users.data.local.dao.UserDao
import com.nvkhang96.tymexgithubusers.github_users.data.local.entities.UserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.mappers.toUserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.remote.GitHubApi
import kotlinx.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val database: GitHubUserDatabase,
    private val api: GitHubApi,
) : RemoteMediator<Int, UserEntity>() {

    private val userDao: UserDao = database.getUserDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {

            val loadKey = when (loadType) {

                LoadType.REFRESH -> null

                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {

                    val lastItem = state.lastItemOrNull()

                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    lastItem.id
                }
            } ?: (GITHUB_USER_SINCE_DEFAULT_VALUE - 1)

            val responseUsers = api.getUserList(since = loadKey + 1)

            database.withTransaction {

                if (loadType == LoadType.REFRESH) {
                    userDao.clearAll()
                }

                userDao.insertAll(responseUsers.map { it.toUserEntity() })
            }

            MediatorResult.Success(
                endOfPaginationReached = responseUsers.size < GITHUB_USER_PER_PAGE_DEFAULT_VALUE
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}