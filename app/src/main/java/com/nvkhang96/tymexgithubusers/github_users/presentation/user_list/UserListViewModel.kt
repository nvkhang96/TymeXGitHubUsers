package com.nvkhang96.tymexgithubusers.github_users.presentation.user_list

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.domain.use_cases.GetUserListUseCase
import kotlinx.coroutines.flow.Flow

/**
 * A ViewModel responsible for managing the list of users displayed in the UI.
 *
 * This ViewModel utilizes the [GetUserListUseCase] to fetch a paginated list of users
 * and exposes it as a [Flow] of [PagingData] through the `users` property.
 * The UI can observe this flow to display the users and handle pagination seamlessly.
 *
 * @property getUserListUseCase The use case responsible for retrieving the list of users.
 */
class UserListViewModel(
    private val getUserListUseCase: GetUserListUseCase,
) : ViewModel() {

    /**
     * A [Flow] of [PagingData] representing the list of users.
     *
     * The UI can observe this flow to display the users and handle pagination.
     * The [PagingData] is provided by the [GetUserListUseCase] and is designed
     * to be used with Paging 3 for efficient loading and display of large lists.
     */
    val users: Flow<PagingData<User>> = getUserListUseCase()
}