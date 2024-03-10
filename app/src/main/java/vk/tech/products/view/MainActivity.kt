package vk.tech.products.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import vk.tech.products.view.ui.theme.ProductsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ProductsTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Products.route,
                    exitTransition = {
                        ExitTransition.None
                    },
                    enterTransition = {
                        EnterTransition.None
                    }
                ) {
                    composable(Screen.Products.route) {
                        ProductsScreen(navController)
                    }
                    composable(
                        route = Screen.ProductDetails.routeWithArg,
                        arguments = listOf(navArgument(Screen.ProductDetails.argName) {
                            type = NavType.IntType
                        })
                    ) {
                        ProductDetailsScreen(navController, it.arguments?.getInt(Screen.ProductDetails.argName) ?: 0)
                    }
                }
            }
        }
    }
}