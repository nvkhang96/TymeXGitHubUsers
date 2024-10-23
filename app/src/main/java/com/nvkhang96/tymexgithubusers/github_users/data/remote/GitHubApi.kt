package com.nvkhang96.tymexgithubusers.github_users.data.remote

import com.nvkhang96.tymexgithubusers.github_users.data.GITHUB_USER_PER_PAGE_DEFAULT_VALUE
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_detail.UserDetailDto
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_list.UserItemDto

interface GitHubApi {

    suspend fun getUserList(
        since: Long,
        perPage: Int = GITHUB_USER_PER_PAGE_DEFAULT_VALUE,
    ): List<UserItemDto>

    suspend fun getUserDetail(username: String): UserDetailDto

    companion object {
        const val BASE_URL = "api.github.com"
    }
}