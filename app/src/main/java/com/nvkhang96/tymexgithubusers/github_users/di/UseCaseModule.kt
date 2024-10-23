package com.nvkhang96.tymexgithubusers.github_users.di

import com.nvkhang96.tymexgithubusers.github_users.domain.use_cases.GetUserDetailUseCase
import com.nvkhang96.tymexgithubusers.github_users.domain.use_cases.GetUserListUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetUserListUseCase(get()) }
    factory { GetUserDetailUseCase(get()) }
}