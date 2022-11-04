package com.example.weatherappcompose.presentation.ui.commonComposables

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.weatherappcompose.domain.location.Location

@Composable
fun LocationLazyColumnItem(
    location: Location,
    icon: ImageVector
) {
    var isNeedColorChange by remember {
        mutableStateOf(false)
    }
    val startColor = MaterialTheme.colorScheme.onBackground
    val endColor = Color.Green

    val color by animateColorAsState(
        targetValue = if (isNeedColorChange) endColor else startColor,
        animationSpec = tween(500)
    )

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .shadow(4.dp)
            .height(80.dp)
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
/*
                Text(
                    text = location.countryCode,
                    fontSize = 36.sp
                )
*/
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://hatscripts.github.io/circle-flags/flags/${location.countryCode}.svg")
                        .decoderFactory(SvgDecoder.Factory())
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                )
                Spacer(modifier = Modifier.width(24.dp))
                IconButton(
                    onClick = { /*TODO*/
                        isNeedColorChange = !isNeedColorChange
                    },
                    modifier = Modifier,
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = "Save to favorites",
                        tint = color,
                        modifier = Modifier
                            .size(36.dp)
                    )
                }
            }
        }
    }
}

val location = Location(
    id = 0,
    name = "Oslo",
    lat = 0.0,
    long = 0.0,
    countryCode = "NO",
    region = "Oslo County"
)

@Preview
@Composable
fun ItemPreview() {
    LocationLazyColumnItem(location = location, icon = Icons.Filled.Favorite)
}