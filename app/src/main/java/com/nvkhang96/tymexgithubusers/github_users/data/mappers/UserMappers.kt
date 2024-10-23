package com.nvkhang96.tymexgithubusers.github_users.data.mappers

import com.nvkhang96.tymexgithubusers.github_users.data.local.entities.UserEntity
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_detail.UserDetailDto
import com.nvkhang96.tymexgithubusers.github_users.data.remote.dto.get_user_list.UserItemDto
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User

fun UserItemDto.toUserEntity() = UserEntity(
    id = id ?: -1,
    username = login ?: "",
    avatarUrl = avatarUrl ?: "",
    htmlUrl = htmlUrl ?: "",
)

fun UserEntity.toUserModel() = User(
    id = id,
    username = username,
    avatarUrl = avatarUrl,
    htmlUrl = htmlUrl,
    location = location,
    followers = followers,
    following = following,
    blog = blog,
)

fun UserDetailDto.toUserEntity() = UserEntity(
    id = id ?: -1,
    username = login ?: "",
    avatarUrl = avatarUrl ?: "",
    htmlUrl = htmlUrl ?: "",
    location = location ?: "",
    followers = followers ?: 0,
    following = following ?: 0,
    blog = blog ?: "",
)