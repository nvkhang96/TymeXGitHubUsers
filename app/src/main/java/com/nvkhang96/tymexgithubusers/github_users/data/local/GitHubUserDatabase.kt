package com.nvkhang96.tymexgithubusers.github_users.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nvkhang96.tymexgithubusers.github_users.data.local.dao.UserDao
import com.nvkhang96.tymexgithubusers.github_users.data.local.entities.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
abstract class GitHubUserDatabase: RoomDatabase() {
    abstract fun getUserDao(): UserDao
}