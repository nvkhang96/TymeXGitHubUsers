package com.nvkhang96.tymexgithubusers.github_users.domain.models

data class User(
    val id: Long = -1,
    val username: String = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val location: String = "",
    val followers: Long = 0,
    val following: Long = 0,
    val blog: String = "",
)