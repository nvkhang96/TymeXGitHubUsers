package com.nvkhang96.tymexgithubusers.github_users.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.data.GITHUB_USER_PER_PAGE_DEFAULT_VALUE
import com.nvkhang96.tymexgithubusers.github_users.data.local.GitHubUserDatabase
import com.nvkhang96.tymexgithubusers.github_users.data.local.dao.UserDao
import com.nvkhang96.tymexgithubusers.github_users.data.mappers.toUserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.mappers.toUserModel
import com.nvkhang96.tymexgithubusers.github_users.data.paging.UserRemoteMediator
import com.nvkhang96.tymexgithubusers.github_users.data.remote.GitHubApi
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * Implementation of the [UserRepository] interface, providing access to user data.
 *
 * This class interacts with both a remote API ([GitHubApi]) and a local database
 * ([GitHubUserDatabase]) to manage user information. It handles fetching, caching,
 * and updating user details, as well as providing a paginated list of users.
 *
 * @property api The API client used to fetch user data from the remote server.
 * @property database The local database used to cache user information.
 * @property userDao The DAO (Data Access Object) for accessing user data in the database.
 * @property dispatcher The [CoroutineDispatcher] used for executing asynchronous operations.
 *                      Defaults to [Dispatchers.IO].
 */
class UserRepositoryImpl(
    private val api: GitHubApi,
    private val database: GitHubUserDatabase,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : UserRepository {

    /**
     * Retrieves a paginated list of users from the database using Paging 3.
     *
     * This function uses a [Pager] and a [UserRemoteMediator] to load and
     * display a paginated list of users from the database, fetching new pages
     * from the remote API as needed. It maps the database entities to UI models
     * before emitting them in the [Flow].
     *
     * @return A [Flow] of [PagingData] containing [User] objects.
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getUsersPaging(): Flow<PagingData<User>> {
        return Pager(
            config = PagingConfig(
                pageSize = GITHUB_USER_PER_PAGE_DEFAULT_VALUE,
            ),
            remoteMediator = UserRemoteMediator(
                database = database,
                api = api,
            ),
        ) {
            userDao.getUserPagingSource()
        }.flow.map { pagingData ->
            pagingData.map { it.toUserModel() }
        }.flowOn(dispatcher)
    }

    /**
     * Retrieves the details of a user, utilizing both local caching and network fetching.
     *
     * This function first attempts to retrieve the user details from the local cache using the `userDao`.
     * If a cached user is found, it is emitted as a [DataResult.Success] immediately.
     *
     * Subsequently, it makes a network request to fetch the latest user details using the `api`.
     * If the network request is successful, the retrieved user details are emitted as a [DataResult.Success],
     * and the local cache is updated with the new information.
     *
     * In case of any exceptions during the network request, a [DataResult.Error] is emitted,
     * and the function returns without updating the cache.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [Flow] emitting [DataResult] objects, wrapping either a [User] on success or
     *         an error indication on failure.
     */
    override suspend fun getUserDetail(username: String): Flow<DataResult<User>> = flow {
        val cachedUser = userDao.getUserByUsername(username).toUserModel()
        emit(DataResult.Success(cachedUser))

        val responseUser = try {
            api.getUserDetail(username)
        } catch (e: Exception) {
            emit(DataResult.Error(exception = e))
            return@flow
        }

        val responseUserEntity = responseUser.toUserEntity()
        val responseUserModel = responseUserEntity.toUserModel()
        emit(DataResult.Success(responseUserModel))

        userDao.update(responseUserEntity)
    }.flowOn(dispatcher)
}
