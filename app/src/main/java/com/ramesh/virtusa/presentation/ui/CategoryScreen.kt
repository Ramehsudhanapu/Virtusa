package com.ramesh.virtusa.presentation.ui
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramesh.virtusa.presentation.state.UIState
import com.ramesh.virtusa.presentation.viewmodel.GetAllCategoryViewModel

@Composable
fun CategoryScreen(
    viewModel: GetAllCategoryViewModel = hiltViewModel(), navController: (String) -> Unit
) {
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.getAllCategories()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),


    ) {
        if (state.value is UIState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            when (val uiState = state.value) {
                is UIState.Success -> {
                    CategoryItem(
                        category = uiState.data,
                        onClick = navController,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                is UIState.Error -> {
                    Text(
                        text = uiState.message,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red // Change color to indicate error
                    )
                }
                else -> {
                    Text(text = "Unknown state")
                }

            }

        }
    }
}
@Composable
fun CategoryItem(
    category: List<String>, onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        contentPadding = PaddingValues(8.dp)
    )
    {
        items(category) { category ->
            CaterCard(category = category, onClick = { onClick(category) })
        }
8

    }

}
@Composable
fun CaterCard(category: String, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White)
            .clickable { onClick(category) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
        Text(
            text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            letterSpacing = 5.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(30.dp),
            style = MaterialTheme.typography.bodyMedium)

    }
}
}
