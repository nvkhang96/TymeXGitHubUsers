package com.nvkhang96.tymexgithubusers.github_users.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.nvkhang96.tymexgithubusers.github_users.presentation.navigation.GitHubUsersNavHost
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.TymeXGitHubUsersTheme
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KoinContext {
                TymeXGitHubUsersTheme {
                    val navController = rememberNavController()

                    GitHubUsersNavHost(navController = navController)
                }
            }
        }
    }
}