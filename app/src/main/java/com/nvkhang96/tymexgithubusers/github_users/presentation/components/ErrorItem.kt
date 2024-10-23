package com.nvkhang96.tymexgithubusers.github_users.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.nvkhang96.tymexgithubusers.R
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.Dimensions
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.TymeXGitHubUsersTheme

@Composable
fun ErrorItem(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.Spacing.Medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(R.string.error_s, message), color = Color.Red)
        Spacer(modifier = Modifier.height(Dimensions.Spacing.Small))
        Button(onClick = onClickRetry) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun ErrorItemPreview() {
    TymeXGitHubUsersTheme {
        ErrorItem(message = "Error")
    }
}