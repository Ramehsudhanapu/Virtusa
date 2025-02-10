package com.ramesh.virtusa.ui
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramesh.virtusa.presentation.ui.CategoryScreen
import com.ramesh.virtusa.presentation.ui.ProductDetailsScreen
import com.ramesh.virtusa.presentation.ui.ProductListScreen
import com.ramesh.virtusa.utilities.GeneralRoutes


@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GeneralRoutes.Category.route
    )
    {
        composable(route = GeneralRoutes.Category.route)
        {
            CategoryScreen(
                navController = { categoryName ->
                    navController.navigate(GeneralRoutes.Category.createRoute(categoryName))
                }
            )

        }
        composable(
            route = GeneralRoutes.Category.createRoute("{categoryName}"),
            arguments = listOf(navArgument("categoryName") {
                type =
                    NavType.StringType
            }
            )
        ) {
            val categoryName = it.arguments?.getString("categoryName") ?: ""
            ProductListScreen(categoryName = categoryName,
                navController = { productId ->
                    navController.navigate(GeneralRoutes.Details.createDetailRoute(productId))

        })
        }
        composable(
            route = GeneralRoutes.Details.route,
            arguments = listOf(navArgument("productId") {
                type = NavType.IntType
            })

        )
        {
            val productId = it.arguments?.getInt("productId") ?: 0
            ProductDetailsScreen(productId = productId)
        }
    }
}