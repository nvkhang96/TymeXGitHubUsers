package com.nvkhang96.tymexgithubusers.github_users.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    val id: Long = -1,
    @PrimaryKey val username: String = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val location: String = "",
    val followers: Long = 0,
    val following: Long = 0,
    val blog: String = "",
)