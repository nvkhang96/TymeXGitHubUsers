package com.nvkhang96.tymexgithubusers.github_users.domain.repositories

import androidx.paging.PagingData
import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import kotlinx.coroutines.flow.Flow

/**
 * A repository interface for accessing user data.
 *
 * This interface provides methods for retrieving user data from the database,
 * including a paginated list of users and details for a specific user.
 */
interface UserRepository {

    /**
     * Retrieves a paginated list of users from the database.
     *
     * This function returns a [Flow] of [PagingData] containing [User] objects.
     * The [PagingData] is designed to be used with Paging 3 for efficient loading
     * and display of large lists in UI elements like `RecyclerView`.
     *
     * @return A [Flow] of [PagingData] containing [User] objects.
     */
    fun getUsersPaging(): Flow<PagingData<User>>

    /**
     * Retrieves the details of a specific user by their username.
     *
     * This function fetches the detailed information of a user identified by the provided `username`.
     * It returns a [Flow] of [DataResult] objects, which encapsulate the result of the operation,
     * including potential success ([DataResult.Success]) or failure ([DataResult.Error]) scenarios.
     *
     * The emitted [DataResult] will contain a [User] object upon successful retrieval.
     *
     * @param username The username of the user to retrieve details for.
     * @return A [Flow] emitting [DataResult] objects, wrapping either a [User] on success or
     *         an error indication on failure.
     */
    suspend fun getUserDetail(username: String): Flow<DataResult<User>>
}