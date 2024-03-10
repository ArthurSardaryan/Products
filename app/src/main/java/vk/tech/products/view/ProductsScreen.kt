package vk.tech.products.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import vk.tech.products.R
import vk.tech.products.view.ui.theme.CurrentGray
import vk.tech.products.view.ui.theme.CurrentLightGrayAlpha
import vk.tech.products.view.ui.theme.CurrentRedLight

val currentSfRoundedFf = FontFamily(
    Font(R.font.sheldon_light_sf_rounded, FontWeight.Light),
    Font(R.font.sheldon_regular_sf_rounded, FontWeight.Normal),
    Font(R.font.sheldon_medium_sf_rounded, FontWeight.Medium),
    Font(R.font.sheldon_bold_sf_rounded, FontWeight.Bold),
    Font(R.font.sheldon_heavy_sf_rounded, FontWeight.ExtraBold),
    Font(R.font.sheldon_black_sf_rounded, FontWeight.Black),
)

@Composable
fun ProductsScreen(
    navHostController: NavHostController,
) {
    val viewModel: ProductViewModel = viewModel()
    val currentWindowInfo = rememberCurrentWindowInfo()
    val adaptiveDp = currentWindowInfo.currentDp
    val adaptiveSp = currentWindowInfo.currentSp
    val categories by viewModel.categories.collectAsState()
    val productsScreenState by viewModel.products.collectAsState(ProductsScreenState.Initial)
    var isFilterExpanded by remember { mutableStateOf(false) }
    var currentFilter by remember { mutableStateOf("") }
    var searchText by remember {
        mutableStateOf("")
    }
    val products by remember {
        derivedStateOf {
            (productsScreenState as ProductsScreenState.Success).products.filter {
                it.category == currentFilter || currentFilter.isEmpty()
            }
        }
    }
    var isSearchMode by remember {
        mutableStateOf(false)
    }
    val state = productsScreenState
    LaunchedEffect(isSearchMode) {
        if (!isSearchMode) {
            viewModel.searchText("")
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = CurrentGray),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(10 * adaptiveDp))
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16 * adaptiveDp)
                    .height(68 * adaptiveDp),
                onClick = {

                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                if (isSearchMode) {
                    IconButton(
                        onClick = {
                            isSearchMode = false
                        },
                        modifier = Modifier
                            .size(32 * adaptiveDp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.current_back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16 * adaptiveDp)
                        )
                    }
                    TextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        shape = CircleShape,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledIndicatorColor = Color.Black,
                            focusedIndicatorColor = Color.Black,
                            unfocusedIndicatorColor = Color.Black,
                            errorIndicatorColor = Color.Black
                        ),
                        modifier = Modifier.width(150 * adaptiveDp),
                        textStyle = TextStyle(
                            fontFamily = currentSfRoundedFf,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16 * adaptiveSp,
                        ),
                        maxLines = 1,
                        singleLine = true,
                        placeholder = {

                            Text(
                                text = "Search",
                                fontFamily = currentSfRoundedFf,
                                fontWeight = FontWeight.Normal,
                                fontSize = 16 * adaptiveSp
                            )
                        }
                    )
                    IconButton(
                        onClick = {
                            viewModel.searchText(searchText)
                        },
                        modifier = Modifier
                            .size(36 * adaptiveDp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .size(28 * adaptiveDp)
                        )
                    }
                }
                else {
                    Text(
                        text = "Products",
                        fontFamily = currentSfRoundedFf,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18 * adaptiveSp,
                        modifier = Modifier
                    )
                    IconButton(
                        onClick = { isFilterExpanded = !isFilterExpanded },
                        modifier = Modifier
                            .size(36 * adaptiveDp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (isFilterExpanded) {
                                    R.drawable.current_filter_expanded
                                }
                                else {
                                    R.drawable.current_filter
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32 * adaptiveDp)
                        )
                    }
                    IconButton(
                        onClick = { isSearchMode = true },
                        modifier = Modifier
                            .offset(x = 48 * adaptiveDp)
                            .size(36 * adaptiveDp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32 * adaptiveDp)
                        )
                    }
                }

            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(
                    if (currentWindowInfo.currentWidthType == CurrentWindowInfo.CurrentType.COMPACT) {
                        2
                    }
                    else {
                        3
                    }
                ),
                modifier = Modifier
                    .padding(top = 10 * adaptiveDp)
            ) {
                when (state) {
                    is ProductsScreenState.Error -> {
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            Text(
                                text = "Error, try again",
                                fontFamily = currentSfRoundedFf,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(
                                        24 * adaptiveDp
                                    ),
                                fontSize = 18 * adaptiveSp,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    ProductsScreenState.Initial -> {}
                    ProductsScreenState.Loading -> {
                        items(10) {
                            ProductCard()
                        }
                    }

                    is ProductsScreenState.Success -> {
                        items(products) {
                            ProductCard(product = it) {
                                navHostController.navigate(Screen.ProductDetails.withArgs(it.id))
                            }
                        }
                        item(
                            span = { GridItemSpan(maxLineSpan) }
                        ) {
                            if (!state.nextDataIsLoading && !state.postsIsOver) {
                                viewModel.nextDataNeeded()
                            }
                        }
                    }
                }
            }
        }
        if (isFilterExpanded) {
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme.copy(surface = Color.Transparent),
                shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
            ) {
                Column(
                    modifier = Modifier
                        .size(240 * adaptiveDp)
                        .align(Alignment.TopEnd)
                        .padding(horizontal = 20 * adaptiveDp)
                        .padding(top = 64 * adaptiveDp)
                        .verticalScroll(rememberScrollState())
                ) {
                    categories.forEachIndexed { index, it ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = it,
                                    fontFamily = currentSfRoundedFf,
                                    fontWeight = if (currentFilter == it) {
                                        FontWeight.Black
                                    }
                                    else {
                                        FontWeight.Medium
                                    },
                                    fontSize = 12 * adaptiveSp,
                                    color = Color.Black
                                )
                            },
                            onClick = {
                                currentFilter = if (currentFilter == it) "" else it
                                isFilterExpanded = !isFilterExpanded
                            },
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topStart = if (index == 0) 25 * adaptiveDp else 0.dp,
                                        topEnd = if (index == 0) 25 * adaptiveDp else 0.dp,
                                        bottomStart = if (index == categories.lastIndex) 25 * adaptiveDp else 0.dp,
                                        bottomEnd = if (index == categories.lastIndex) 25 * adaptiveDp else 0.dp
                                    )
                                )
                                .border(
                                    BorderStroke((0.5).dp, CurrentLightGrayAlpha),
                                    RoundedCornerShape(
                                        topStart = if (index == 0) 25 * adaptiveDp else 0.dp,
                                        topEnd = if (index == 0) 25 * adaptiveDp else 0.dp,
                                        bottomStart = if (index == categories.lastIndex) 25 * adaptiveDp else 0.dp,
                                        bottomEnd = if (index == categories.lastIndex) 25 * adaptiveDp else 0.dp
                                    )
                                )
                                .background(if (currentFilter == it) CurrentRedLight else Color.White),
                            colors = MenuDefaults.itemColors()
                        )
                    }
                }
            }
        }
    }
}