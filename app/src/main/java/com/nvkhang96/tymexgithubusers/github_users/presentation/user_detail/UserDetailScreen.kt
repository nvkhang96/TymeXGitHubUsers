@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nvkhang96.tymexgithubusers.R
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.presentation.components.ErrorItem
import com.nvkhang96.tymexgithubusers.github_users.presentation.components.UserCard
import com.nvkhang96.tymexgithubusers.github_users.presentation.components.UserCardInfo
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.Dimensions
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.TymeXGitHubUsersTheme
import com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail.UserDetailViewModel.Intent
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDetailScreen(
    username: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: UserDetailViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
) {
    LaunchedEffect(Unit) {
        viewModel.onIntent(Intent.GetUser(username))
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    UserDetailScreenContent(
        state = state,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
        onClickRetry = {
            viewModel.onIntent(Intent.GetUser(username))
        },
        onNavigateBack = onNavigateBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDetailScreenContent(
    state: UserDetailViewModel.State = UserDetailViewModel.State(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClickRetry: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
) {
    val user = state.user
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.user_detail),
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(Dimensions.Spacing.Medium),
        ) {
            with(sharedTransitionScope) {
                UserCard(
                    user = user,
                    infos = listOf(UserCardInfo.Location),
                    modifier = Modifier
                        .sharedElement(
                            sharedTransitionScope.rememberSharedContentState("users/${user.username}"),
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                )
            }
            Spacer(modifier = Modifier.height(Dimensions.Spacing.Large))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        modifier = Modifier
                            .size(Dimensions.ImageSize.Medium)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                            )
                            .padding(Dimensions.Spacing.Small)
                    )
                    Text(
                        text = user.followers.toString(),
                    )
                    Text(
                        text = stringResource(R.string.follower),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(Dimensions.ImageSize.Medium)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = CircleShape,
                            )
                            .padding(Dimensions.Spacing.Small)
                    )
                    Text(
                        text = user.following.toString(),
                    )
                    Text(
                        text = stringResource(R.string.following),
                    )
                }
            }
            Spacer(modifier = Modifier.height(Dimensions.Spacing.Large))
            Text(
                text = stringResource(R.string.blog),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = user.blog,
            )
            AnimatedVisibility(state.error != null) {
                ErrorItem(
                    message = state.error ?: stringResource(R.string.unknown_error),
                    onClickRetry = onClickRetry,
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun UserDetailScreenContentPreview() {
    TymeXGitHubUsersTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                UserDetailScreenContent(
                    state = UserDetailViewModel.State(
                        user = User(
                            username = "mojombo",
                            htmlUrl = "https://github.com/mojombo",
                            location = "San Francisco",
                            followers = 24047,
                            following = 11,
                            blog = "http://tom.preston-werner.com",
                        ),
                        error = "Error",
                    ),
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this,
                )
            }
        }
    }
}