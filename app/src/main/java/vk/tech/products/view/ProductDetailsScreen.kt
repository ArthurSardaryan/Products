package vk.tech.products.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import vk.tech.products.R
import vk.tech.products.view.ui.theme.CurrentDarkGray
import vk.tech.products.view.ui.theme.CurrentGray

@Composable
fun ProductDetailsScreen(currentNavController: NavHostController, id: Int) {
    val viewModel: ProductDetailsViewModel = viewModel(
        factory = ViewModelFactory(id)
    )
    val productState by viewModel.productState.collectAsState(ProductDetailsScreenState.Initial)
    val currentWindowInfo = rememberCurrentWindowInfo()
    val currentDp = currentWindowInfo.currentDp
    val currentSp = currentWindowInfo.currentSp
    var isCurrentClickedBack by remember {
        mutableStateOf(false)
    }
    Box(modifier = Modifier.fillMaxSize()) {

        when (val state = productState) {
            is ProductDetailsScreenState.Error -> {}
            ProductDetailsScreenState.Initial -> {}
            ProductDetailsScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CurrentGray)
                ) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is ProductDetailsScreenState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = CurrentGray),
                ) {
                    ElevatedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16 * currentDp)
                            .padding(bottom = 16 * currentDp),
                        onClick = {},
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = CurrentDarkGray),
                        shape = RoundedCornerShape(40),
                        contentPadding = PaddingValues(),
                        elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8 * currentDp)
                        ) {
                            IconButton(
                                onClick = {
                                    if (isCurrentClickedBack) return@IconButton
                                    isCurrentClickedBack = true
                                    currentNavController.popBackStack()
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .size(32 * currentDp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.current_back),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(16 * currentDp)
                                )
                            }
                            Text(
                                text = state.product.title,
                                color = Color.White,
                                fontFamily = currentSfRoundedFf,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16 * currentSp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.weight(4.5f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    AsyncImage(
                        model = state.product.thumbnail,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16 * currentDp)
                            .height(200 * currentDp)
                            .clip(
                                RoundedCornerShape(10)
                            )
                            .border(
                                BorderStroke(2.dp, Color.Black),
                                RoundedCornerShape(10)
                            ),
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.placeholder_image),
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16 * currentSp,
                                )
                            ) {
                                append("Price: ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14 * currentSp,
                                )
                            ) {
                                append(state.product.price.toString())
                            }
                        },
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 8 * currentDp)
                            .padding(horizontal = 16 * currentDp),
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14 * currentSp,
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16 * currentSp,
                                )
                            ) {
                                append("Rating: ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14 * currentSp,
                                )
                            ) {
                                append(state.product.rating.toString())
                                append(" ★")
                            }
                        },
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 8 * currentDp)
                            .padding(horizontal = 16 * currentDp),
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14 * currentSp,
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16 * currentSp,
                                )
                            ) {
                                append("Brand: ")
                            }
                            withStyle(
                                SpanStyle(
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14 * currentSp,
                                )
                            ) {
                                append(state.product.brand)
                            }
                        },
                        color = Color.White,
                        modifier = Modifier
                            .padding(top = 8 * currentDp)
                            .padding(horizontal = 16 * currentDp),
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14 * currentSp,
                    )
                    Text(
                        text = state.product.description,
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 8 * currentDp)
                            .padding(horizontal = 16 * currentDp),
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14 * currentSp,
                        lineHeight = 16 * currentSp
                    )
                    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                        items(state.product.images) {
                            AsyncImage(
                                model = it,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(200 * currentDp)
                                    .width(100 * currentDp)
                                    .padding(16 * currentDp),
                                contentScale = ContentScale.FillBounds,
                                placeholder = painterResource(id = R.drawable.placeholder_image),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16 * currentDp))
                }
            }
        }
    }
}
