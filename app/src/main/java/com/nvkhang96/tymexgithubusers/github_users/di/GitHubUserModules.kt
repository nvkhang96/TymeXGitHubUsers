package com.nvkhang96.tymexgithubusers.github_users.di

import org.koin.dsl.module

val githubUserModules = module {
    includes(
        networkModule,
        databaseModule,
        useCaseModule,
        viewModelModule,
    )
}