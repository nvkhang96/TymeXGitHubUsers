package com.nvkhang96.tymexgithubusers.app

import android.app.Application
import com.nvkhang96.tymexgithubusers.github_users.di.githubUserModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androix.startup.KoinStartup.onKoinStartup
import org.koin.core.context.startKoin

@Suppress("OPT_IN_USAGE")
class TymeXApplication: Application() {

    init {
        onKoinStartup {
            androidLogger()
            androidContext(this@TymeXApplication)
            modules(githubUserModules)
        }
    }
}