@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.nvkhang96.tymexgithubusers.github_users.presentation.user_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.nvkhang96.tymexgithubusers.R
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.presentation.components.ErrorItem
import com.nvkhang96.tymexgithubusers.github_users.presentation.components.UserCard
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.Dimensions
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.TymeXGitHubUsersTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserListScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: UserListViewModel = koinViewModel(),
    onNavigateToUserDetail: (String) -> Unit = {},
) {
    UserListScreenContent(
        userPagingDataFlow = viewModel.users,
        onNavigateToUserDetail = onNavigateToUserDetail,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreenContent(
    userPagingDataFlow: Flow<PagingData<User>>,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onNavigateToUserDetail: (String) -> Unit = {},
) {
    val users = userPagingDataFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {},
                title = {
                    Text(
                        text = stringResource(R.string.github_users),
                        fontWeight = FontWeight.Bold,
                    )
                },
                modifier = Modifier.background(Color.Transparent)
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            items(
                users.itemCount,
                key = users.itemKey { it.username },
            ) { index ->
                val user = users[index]
                if (user != null) {
                    with(sharedTransitionScope) {
                        UserCard(
                            user = user,
                            onClick = { onNavigateToUserDetail(user.username) },
                            modifier = Modifier
                                .padding(
                                    horizontal = Dimensions.Spacing.Medium,
                                    vertical = Dimensions.Spacing.Small,
                                )
                                .sharedElement(
                                    rememberSharedContentState("users/${user.username}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                )
                        )
                    }
                } else {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(Dimensions.ImageSize.Medium)
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            users.apply {
                when {
                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorItem(
                                message = (loadState.refresh as LoadState.Error).error.message
                                    ?: "Error",
                                onClickRetry = { retry() }
                            )
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        item {
                            ErrorItem(
                                message = (loadState.append as LoadState.Error).error.message
                                    ?: "Error",
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun UserListScreenPreview() {
    val users = listOf<User>(
        User(
            username = "mojombo",
            htmlUrl = "https://github.com/mojombo",
        ),
        User(
            username = "defunkt",
            htmlUrl = "https://github.com/defunkt",
        ),
        User(
            username = "pjhyett",
            htmlUrl = "https://github.com/pjhyett",
        )
    )
    val userPagingData = PagingData.from(users)
    val userPagingFlow = MutableStateFlow(userPagingData)

    TymeXGitHubUsersTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                UserListScreenContent(
                    userPagingDataFlow = userPagingFlow,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                )
            }
        }
    }
}