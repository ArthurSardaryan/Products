package vk.tech.products.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import coil.compose.AsyncImage
import vk.tech.products.R
import vk.tech.products.domain.model.Product
import vk.tech.products.view.ui.theme.CurrentDarkGray
import vk.tech.products.view.ui.theme.CurrentLightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard(
    product: Product,
    currentOnClick: () -> Unit = {},
) {
    val currentWindowInfo = rememberCurrentWindowInfo()
    val currentDp = currentWindowInfo.currentDp
    val currentSp = currentWindowInfo.currentSp
    var isShowAll by remember {
        mutableStateOf(false)
    }
    ElevatedCard(
        onClick = currentOnClick,
        colors = CardDefaults.elevatedCardColors(
            containerColor = CurrentDarkGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .width(180 * currentDp)
            .then(
                if (isShowAll) Modifier else Modifier.height(180 * currentDp)
            )
            .padding(6 * currentDp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(90 * currentDp)
                .padding(horizontal = 12 * currentDp)
                .padding(top = 12 * currentDp)
                .clip(RoundedCornerShape(15))
        ) {
            AsyncImage(
                model = product.thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.placeholder_image),
            )
        }
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    Modifier
                        .padding(start = 12 * currentDp)
                        .width(100 * currentDp)
                ) {
                    Text(
                        text = product.title,
                        color = Color.White,
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14 * currentSp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Text(
                        text = if (product.price == 0) "" else "Price: ${product.price}",
                        fontSize = if (currentSp == 2.sp) 12.sp else 10.sp,
                        color = CurrentLightGray,
                        fontWeight = FontWeight.Normal,
                    )
                }
                IconButton(
                    onClick = currentOnClick,
                    modifier = Modifier
                        .padding(horizontal = 4 * currentDp)
                        .size(58 * currentDp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.current_arrow_right_circle),
                        contentDescription = null,
                        modifier = Modifier.size(78 * currentDp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
            IconButton(
                onClick = {
                    isShowAll = !isShowAll
                },
                modifier = Modifier
                    .size(24 * currentDp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = if (isShowAll) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.size(24 * currentDp),
                    tint = CurrentLightGray
                )
            }

            if (isShowAll) {
                Text(
                    text = product.description,
                    fontSize = if (currentSp == 2.sp) 12.sp else 10.sp,
                    color = CurrentLightGray,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(12 * currentDp),
                    lineHeight = 12.sp
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCard() {
    val currentWindowInfo = rememberCurrentWindowInfo()
    val currentDp = currentWindowInfo.currentDp

    ElevatedCard(
        onClick = {},
        colors = CardDefaults.elevatedCardColors(
            containerColor = CurrentDarkGray
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(15),
        modifier = Modifier
            .width(180 * currentDp)
            .height(180 * currentDp)
            .padding(8 * currentDp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}