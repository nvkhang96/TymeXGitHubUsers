package com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_list


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserItemDto(
    @SerialName("login")
    val login: String? = null,
    @SerialName("id")
    val id: Long? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
)