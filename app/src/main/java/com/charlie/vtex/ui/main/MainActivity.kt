package com.charlie.vtex.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.charlie.vtex.base.AppTopBar
import com.charlie.vtex.ui.ProfileScreen
import com.charlie.vtex.ui.home.HomeScreen
import com.charlie.vtex.ui.node.AllNodeScreen
import com.charlie.vtex.ui.node.NodeScreen
import com.charlie.vtex.ui.theme.VTEXTheme
import com.charlie.vtex.ui.topic.TopicItemDetails

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    //返回back堆栈的顶部条目
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    //返回当前route
    val currentRoute = navBackStackEntry?.destination?.route ?: BottomNavItem.Home.route

    val bottomNavRoute = remember { mutableStateOf<BottomNavItem>(BottomNavItem.Home) }

    if (isMainScreen(currentRoute)) {
        val bottomItems = listOf(BottomNavItem.Home, BottomNavItem.Node, BottomNavItem.Profile)
        VTEXTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                Scaffold(
                    topBar = {
                        AppTopBar(title = bottomNavRoute.value.title)
                    },
                    bottomBar = {
                        BottomAppBar(
                            backgroundColor = VTEXTheme.colors.bottomBar,
                            contentColor = VTEXTheme.colors.bottomBar,
                        ) {
                            BottomNavigation {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentRoute = navBackStackEntry?.destination?.route

                                bottomItems.forEach { item ->
                                    BottomNavigationItem(
                                        selectedContentColor = VTEXTheme.colors.mainTabSelected,
                                        unselectedContentColor = VTEXTheme.colors.mainTabUnSelected,
                                        modifier = Modifier.background(VTEXTheme.colors.bottomBar),
                                        onClick = {
                                            if (bottomNavRoute.value == item) {
                                                return@BottomNavigationItem
                                            }
                                            //记录当前的Item
                                            bottomNavRoute.value = item

                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                        selected = currentRoute == item.route,
                                        icon = {
                                            Icon(
                                                item.icon,
                                                contentDescription = null,
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    },
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavigationGraph(navHostController = navController)
                    }
                }
            }
        }
    } else {
        NavigationGraph(navHostController = navController)
    }
}

fun isMainScreen(route: String): Boolean = when (route) {
    BottomNavItem.Home.route,
    BottomNavItem.Node.route,
    BottomNavItem.Profile.route -> true
    else -> false
}

@Composable
fun NavigationGraph(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = RouteKey.Main.route) {
        navigation(
            startDestination = BottomNavItem.Home.route,
            route = RouteKey.Main.route
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(navHostController)
                BackHandler {

                }
            }
            composable(BottomNavItem.Node.route) {
                AllNodeScreen(navHostController)
            }
            composable(BottomNavItem.Profile.route) {
                ProfileScreen()
            }
        }

        composable(
            route = "${RouteKey.TopicItemDetails.route}/{itemId}", arguments = listOf(
                navArgument("itemId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            TopicItemDetails(navHostController = navHostController, backStackEntry.arguments)
        }

        composable(
            route = "${RouteKey.Tab.route}/{tab}", arguments = listOf(
                navArgument("tab") {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            NodeScreen(navHostController = navHostController, navBackStackEntry.arguments)
        }

    }

}

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {

    object Home : BottomNavItem("V2EX", Icons.Filled.Home, "Home")
    object Node : BottomNavItem("Node", Icons.Filled.MoreVert, "Node")
    object Profile : BottomNavItem("Profile", Icons.Filled.Person, "Profile")
}


/**
 * 页面类
 * */
enum class RouteKey(val route: String) {
    Main("main"),
    Login("login"),
    WebView("webview"),
    Search("search"),
    TopicItemDetails("topic_item_details"),
    Tab("tab"),
}

