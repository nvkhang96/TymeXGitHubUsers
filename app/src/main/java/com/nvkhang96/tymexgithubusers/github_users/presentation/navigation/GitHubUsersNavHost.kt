package com.nvkhang96.tymexgithubusers.github_users.presentation.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail.UserDetailScreen
import com.nvkhang96.tymexgithubusers.github_users.presentation.user_list.UserListScreen
import kotlinx.serialization.Serializable

@Serializable
object UserList

@Serializable
data class UserDetail(val username: String)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun GitHubUsersNavHost(navController: NavHostController) {
    SharedTransitionLayout {
        NavHost(navController, startDestination = UserList) {
            composable<UserList> {
                UserListScreen(
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                    onNavigateToUserDetail = { username ->
                        navController.navigate(UserDetail(username))
                    },
                )
            }
            composable<UserDetail> { backStackEntry ->
                val username = backStackEntry.arguments?.getString("username") ?: ""
                UserDetailScreen(
                    username = username,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                )
            }
        }
    }
}