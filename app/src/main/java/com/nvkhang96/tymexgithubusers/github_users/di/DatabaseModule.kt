package com.nvkhang96.tymexgithubusers.github_users.di

import androidx.room.Room
import com.nvkhang96.tymexgithubusers.github_users.data.local.GitHubUserDatabase
import com.nvkhang96.tymexgithubusers.github_users.data.local.dao.UserDao
import com.nvkhang96.tymexgithubusers.github_users.data.repositories.UserRepositoryImpl
import com.nvkhang96.tymexgithubusers.github_users.domain.repositories.UserRepository
import org.koin.dsl.module

val databaseModule = module {
    single<GitHubUserDatabase> {
        Room.databaseBuilder(
            get(),
            GitHubUserDatabase::class.java, "github-user-db"
        ).build()
    }

    single<UserDao> {
        get<GitHubUserDatabase>().getUserDao()
    }

    single<UserRepository> {
        UserRepositoryImpl(get(), get(), get())
    }
}