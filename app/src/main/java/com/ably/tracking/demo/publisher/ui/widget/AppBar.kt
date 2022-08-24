package com.ably.tracking.demo.publisher.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.theme.AATPublisherDemoTheme

@Preview
@Composable
fun AATAppBar(actions: @Composable RowScope.() -> Unit = {}) = AATPublisherDemoTheme {
    TopAppBar(
        actions = actions,
        title = {
            Image(
                painter = painterResource(
                    id = R.drawable.header_logo_with_title
                ),
                contentDescription = stringResource(
                    id = R.string.image_content_description_header_logo_with_title
                )
            )
        }
    )
}
