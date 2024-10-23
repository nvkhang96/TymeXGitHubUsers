package com.nvkhang96.tymexgithubusers.github_users.data.remote.request

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource("/users")
class Users(
    val since: Long? = null,
    @SerialName("per_page") val perPage: Int? = null,
) {
    @Resource("{login_username}")
    class LoginUsername(
        val parent: Users = Users(),
        @SerialName("login_username")
        val loginUsername: String,
    )
}