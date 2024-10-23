package com.nvkhang96.tymexgithubusers.github_users.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.nvkhang96.tymexgithubusers.github_users.domain.models.User
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.Dimensions
import com.nvkhang96.tymexgithubusers.github_users.presentation.ui.theme.TymeXGitHubUsersTheme

@Composable
fun UserCard(
    user: User,
    infos: List<UserCardInfo> = listOf(UserCardInfo.Url),
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        onClick = {
            onClick()
        },
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.Spacing.Medium)
        ) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = Dimensions.Spacing.Small)
                    .size(Dimensions.ImageSize.Large)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(Dimensions.CornerRadius.Small),
                    )
                    .padding(Dimensions.Spacing.Small)
                    .clip(CircleShape)
            )
            Column {
                Text(
                    text = user.username,
                    fontWeight = FontWeight.Bold,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = Dimensions.Spacing.Small)
                )
                if (infos.contains(UserCardInfo.Url)) {
                    Text(
                        buildAnnotatedString {
                            withLink(
                                LinkAnnotation.Url(
                                    user.htmlUrl,
                                    TextLinkStyles(
                                        style = SpanStyle(
                                            color = Color.Blue,
                                            textDecoration = TextDecoration.Underline,
                                        )
                                    )
                                )
                            ) {
                                append(user.htmlUrl)
                            }
                        }
                    )
                }
                if (infos.contains(UserCardInfo.Location)
                    && user.location.isNotBlank()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = user.location,
                            style = typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}

enum class UserCardInfo {
    Url, Location
}

@Preview
@Composable
private fun UserCardPreview() {
    TymeXGitHubUsersTheme {
        Column {
            val user = User(
                username = "mojombo",
                htmlUrl = "https://github.com/mojombo",
                location = "San Francisco",

            )

            UserCard(user)

            Spacer(modifier = Modifier.height(Dimensions.Spacing.Medium))

            UserCard(
                user = user,
                infos = listOf(UserCardInfo.Location)
            )
        }
    }
}