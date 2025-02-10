package com.ramesh.virtusa.presentation.ui

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ramesh.virtusa.R
import com.ramesh.virtusa.data.model.ProductResponse
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.presentation.viewmodel.GetProductsByCategoryNameViewModel


import kotlin.text.toIntOrNull

@Composable
fun ProductListScreen(
    categoryName: String?,
    modifier: Modifier = Modifier,
    navController: (Int) -> Unit,
    viewModel: GetProductsByCategoryNameViewModel = hiltViewModel()
) {

    val state = viewModel.uiState.collectAsState()
    val productList = rememberSaveable(saver = ProductResponseListSaver) {
        mutableStateListOf()
    }

    LaunchedEffect(key1 = categoryName) {
        if (categoryName != null) {
            viewModel.getProductsByCategoryName(categoryName)
        }

    }
    LaunchedEffect(key1 = state) {

        if (state.value is UIState.Success<*>) {
            productList.clear()
            productList.addAll((state.value as UIState.Success<*>).data as Collection<ProductResponse>)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)

    )
    {
        if (state.value is UIState.Loading) {
            CircularProgressIndicator(modifier=Modifier.align(Alignment.Center))
        }
        else
        {
            when(val value=state.value){
                is UIState.Success-> {

                    val product = value.data
                    ProductListItem(
                        product = product,
                        onclick = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center),

                        )


                }

                is UIState.Error -> {
                    Text(text = value.message)
                }

                else -> {
                    Text(text = "Unknown state")

                }
            }


        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListItem(product: List<ProductResponse>, modifier: Modifier, onclick: (Int) -> Unit) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(140.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content =
            {
                items(product, key = { it.id ?: -1 }) { product ->

                    ProductItem(
                        product = product,
                        onclick=onclick,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                            .animateItemPlacement(tween(durationMillis = 300))
                    )
                }
            }, contentPadding = PaddingValues(8.dp)

        )
    }
}

@Composable
fun ProductItem(product: ProductResponse, modifier: Modifier,onclick: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        onClick = { onclick(product.id?:0)},
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

        )
    {
        Column(
            modifier = modifier.fillMaxSize(),

            ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.image)
                    .placeholder(coil.compose.base.R.drawable.notification_action_background)
                    .crossfade(true)
                    .size(150, 150)
                    .bitmapConfig(android.graphics.Bitmap.Config.RGB_565)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier.padding(48.dp)
                    )

                }, contentDescription = stringResource(R.string.thumnail),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)

            )
            Divider(color = Color.Gray, thickness = 1.dp)
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = product.title ?: "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = Color.Black
                )
                Text(
                    text = product.price.toString()?:"",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )


            }

        }

    }
}
val ProductResponseListSaver: Saver<MutableList<ProductResponse>, *> =
    listSaver(
        save = { it.map { productResponse -> productResponse.id.toString() } },
        restore = { restoredList ->
            // In a real app, you'd fetch the ProductResponse objects based on the restored IDs
            restoredList.map { id -> ProductResponse(id = id.toIntOrNull()) }.toMutableStateList()
        }
    )







