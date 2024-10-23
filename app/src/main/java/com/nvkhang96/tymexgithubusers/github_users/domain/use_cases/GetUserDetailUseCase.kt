package com.nvkhang96.tymexgithubusers.github_users.domain.use_cases

import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

/**
 * A use case for retrieving the details of a user.
 *
 * This use case takes a [GetUserDetailUseCase.Input] object containing the username
 * as input and returns a [Flow] of [DataResult] wrapping a [User] object.
 *
 * @property userRepository The repository used to retrieve user details.
 */
class GetUserDetailUseCase(private val userRepository: UserRepository) {

    /**
     * Input data class for the use case.
     *
     * @property username The username of the user to retrieve details for.
     */
    data class Input(val username: String)

    /**
     * Executes the use case.
     *
     * @param input The input data for the use case.
     * @return A [Flow] of [DataResult] wrapping a [User] object.
     */
    suspend operator fun invoke(input: Input): Flow<DataResult<User>> {
        return userRepository.getUserDetail(input.username)
    }
}