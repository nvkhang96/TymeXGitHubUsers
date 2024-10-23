package com.nvkhang96.tymexgithubusers.github_users.domain.use_cases

import androidx.paging.PagingData
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * A use case that retrieves a paginated list of users.
 *
 * This use case fetches users from the database using Paging 3, providing a stream of
 * [PagingData] that can be collected and displayed in a UI element like a
 * `RecyclerView` with a `PagingDataAdapter`.
 *
 * @return A [Flow] of [PagingData] containing [User] objects.
 */
class GetUserListUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<PagingData<User>> {
        return userRepository.getUsersPaging()
    }
}