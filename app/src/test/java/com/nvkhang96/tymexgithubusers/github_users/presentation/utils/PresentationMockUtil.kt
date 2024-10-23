package com.nvkhang96.tymexgithubusers.github_users.presentation.utils

import com.nvkhang96.tymexgithubusers.github_users.domain.models.User

object PresentationMockUtil {

    val users = listOf(
            User(
                id = 0,
                username = "username0",
                avatarUrl = "avatarUrl",
                htmlUrl = "htmlUrl",
                location = "location",
                followers = 10,
                following = 10,
                blog = "blog",
            ),
        )

    val exception = Exception()
}