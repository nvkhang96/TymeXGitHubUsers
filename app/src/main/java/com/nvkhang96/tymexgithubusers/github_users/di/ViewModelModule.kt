package com.nvkhang96.tymexgithubusers.github_users.di

import com.nvkhang96.tymexgithubusers.github_users.presentation.user_detail.UserDetailViewModel
import com.nvkhang96.tymexgithubusers.github_users.presentation.user_list.UserListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UserListViewModel(get())
    }
    viewModel {
        UserDetailViewModel(get())
    }
}