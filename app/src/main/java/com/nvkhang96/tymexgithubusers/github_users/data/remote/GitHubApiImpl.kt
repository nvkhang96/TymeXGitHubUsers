package com.nvkhang96.tymexgithubusers.github_users.data.remote

import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_detail.UserDetailDto
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_list.UserItemDto
import com.nvkhang96.tymexgithubusers.github_users.data.remote.request.Users
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.withCharset


class GitHubApiImpl(
    private val httpClient: HttpClient,
) : GitHubApi {

    override suspend fun getUserList(since: Long, perPage: Int): List<UserItemDto> {
        return httpClient.get(Users(since = since, perPage = perPage)) {
            contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        }.body()
    }

    override suspend fun getUserDetail(username: String): UserDetailDto {
        return httpClient.get(Users.LoginUsername(loginUsername = username)) {
            contentType(ContentType.Application.Json.withCharset(Charsets.UTF_8))
        }.body()
    }
}