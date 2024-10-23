package com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvkhang96.tymexgithubusers.base.data.utils.DataResult
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.domain.use_cases.GetUserDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A ViewModel responsible for managing the state and logic for displaying user details.
 *
 * This ViewModel uses [GetUserDetailUseCase] to fetch user details and exposes the
 * current state as a [StateFlow] through the [state] property. It handles user
 * interactions through the [onIntent] function, which accepts [Intent] objects.
 */
class UserDetailViewModel(
    private val getUserDetailUseCase: GetUserDetailUseCase,
) : ViewModel() {

    /**
     * Represents the UI state of the user details screen.
     *
     * @property user The [User] object to display. Defaults to an empty [User] object.
     */
    data class State(
        val user: User = User(),
        val error: String? = null,
    )

    /**
     * Represents the possible user interactions (intents) for the screen.
     */
    sealed interface Intent {
        /**
         * Intent to fetch user details by username.
         *
         * @property username The username of the user to fetch details for.
         */
        data class GetUser(val username: String) : Intent
    }

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    /**
     * Handles user interactions (intents) and updates the ViewModel's state accordingly.
     *
     * @param intent The [Intent] representing the user interaction.
     */
    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.GetUser -> {
                getUserDetail(intent.username)
            }
        }
    }

    private fun getUserDetail(username: String) {
        viewModelScope.launch {
            getUserDetailUseCase(GetUserDetailUseCase.Input(username)).collect { result ->
                when (result) {
                    is DataResult.Success -> {
                        _state.update {
                            state.value.copy(
                                user = result.data,
                                error = null,
                            )
                        }
                    }

                    is DataResult.Error -> {
                        _state.update {
                            state.value.copy(
                                error = result.exception?.message,
                            )
                        }
                    }
                }
            }
        }
    }
}