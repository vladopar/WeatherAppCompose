package com.example.weatherappcompose.presentation.ui.commonComposables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.weatherappcompose.domain.location.Location

@Composable
fun LocationLazyColumnItem(
    location: Location,
    icon: ImageVector,
    onClick: () -> Unit,
    onIconClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(10.dp))
            .height(80.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .width(100.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxHeight()
                    .width(200.dp)
            ) {
                Text(
                    text = location.name,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = location.region ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://hatscripts.github.io/circle-flags/flags/${location.countryCode?.lowercase()}.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape
                        )
                )
                Spacer(modifier = Modifier.width(24.dp))
                IconButton(
                    onClick = {
                        onIconClick()
                    },
                    modifier = Modifier,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Save to favorites",
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        }
    }
}