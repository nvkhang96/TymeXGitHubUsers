package com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_detail


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDetailDto(
    @SerialName("login")
    val login: String? = null,
    @SerialName("id")
    val id: Long? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null,
    @SerialName("html_url")
    val htmlUrl: String? = null,
    @SerialName("location")
    val location: String? = null,
    @SerialName("followers")
    val followers: Long? = null,
    @SerialName("following")
    val following: Long? = null,
    @SerialName("blog")
    val blog: String? = null,
)