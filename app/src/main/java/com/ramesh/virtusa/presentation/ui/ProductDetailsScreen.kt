package com.ramesh.virtusa.presentation.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.ramesh.virtusa.data.model.Product
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.presentation.viewmodel.GetProductsByIDViewModel


@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier.fillMaxSize(),
    viewModel: GetProductsByIDViewModel = hiltViewModel()
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(key1 = productId) {
        viewModel.getProductByID(productId)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {

        if (state.value is UIState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            when (val value = state.value) {
                is UIState.Success -> {
                    DetailProdut(product = value.data, modifier = Modifier.fillMaxSize())
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

@Composable
fun DetailProdut(product: Product, modifier: Modifier) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .background(Color.White),

        ) {
        item {
            ImageProductPager(product = product)
            Spacer(modifier = Modifier.height(16.dp))
            TitleProduct(product = product)
        }

    }


}

@Composable
fun TitleProduct(product: Product) {
    Column(
        modifier = Modifier.padding(
            horizontal = 16.dp,
            vertical = 8.dp))
    {
        Text(
            text = product.title ?: "",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            maxLines = 2
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.price.toString() ?: "")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = product.description ?: "")
    }

}

@Composable
fun ImageProductPager(product: Product) {

    val imgurl = product.image
    if (imgurl != null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(imgurl)
                    .placeholder(coil.compose.base.R.drawable.notification_action_background)
                    .crossfade(true).build(),
                loading = {
                    CircularProgressIndicator(
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                },
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize()
            )

        }

    }
}
