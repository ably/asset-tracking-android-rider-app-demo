package com.ably.tracking.demo.publisher.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ably.tracking.demo.publisher.R
import com.ably.tracking.demo.publisher.ui.theme.AATPublisherDemoTheme

@Preview
@Composable
fun AATAppBar() = AATPublisherDemoTheme {
    TopAppBar(
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.header_logo_with_title
            ),
            contentDescription = stringResource(
                id = R.string.image_content_description_header_logo_with_title
            )
        )
    }
}
