package com.nvkhang96.tymexgithubusers.github_users.di

import com.nvkhang96.tymexgithubusers.BuildConfig
import com.nvkhang96.tymexgithubusers.base.utils.LogUtil
import com.nvkhang96.tymexgithubusers.github_users.data.remote.GitHubApi
import com.nvkhang96.tymexgithubusers.github_users.data.remote.GitHubApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {

    single<HttpClient> {
        HttpClient(OkHttp) {

            install(Resources)
            if (BuildConfig.GITHUB_PAT.isNotBlank()) {
                install(Auth) {
                    bearer {
                        loadTokens {
                            BearerTokens(BuildConfig.GITHUB_PAT, "")
                        }
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            LogUtil.d("HttpClient") { message }
                        }
                    }
                    level = LogLevel.BODY
                    sanitizeHeader { header -> header == HttpHeaders.Authorization }
                }
            }

            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = GitHubApi.BASE_URL
                }
            }
        }
    }

    single<GitHubApi> { GitHubApiImpl(get()) }
}