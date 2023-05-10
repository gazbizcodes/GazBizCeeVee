package com.example.gazbizceevee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gazbizceevee.network.APICeeVeeDeserializer
import com.example.gazbizceevee.ui.Screen
import com.example.gazbizceevee.ui.theme.GazBizCeeVeeTheme
import com.example.gazbizceevee.utils.FileUtils
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

class MainActivity : ComponentActivity() {

    private val fileUtils = FileUtils()
    private val deserializer = APICeeVeeDeserializer()

    private val navigationItems = listOf(
        Screen.Home,
        Screen.Experience,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                bottomBar = { CreateBottomNav(rememberNavController()) }
            ) { innerPadding ->
                NavHost(
                    rememberNavController(),
                    startDestination = Screen.Home.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) { Home() }
                    composable(Screen.Experience.route) { Experience() }
                }
            }
        }
        val cv = deserializer.getAPICeeVee()
        cv
    }

    @Composable
    private fun CreateBottomNav(navController: NavHostController) {
        BottomNavigation {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            navigationItems.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = null) },
                    label = { Text(stringResource(screen.resourceId)) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }

    @Composable
    fun Home() {
        val file = fileUtils.getFileFromAssets(this@MainActivity, "cv.pdf")
        file?.let {
            val pdfState = rememberVerticalPdfReaderState(
                resource = ResourceType.Local(file.toUri()),
                isZoomEnable = true
            )

            GazBizCeeVeeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    VerticalPDFReader(
                        state = pdfState,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background)
                    )
                }
            }
        } ?: ErrorScreen("File not found")
    }

    @Composable
    fun Experience() {
        Text(text = "This will contain the experience section of the CV.")
    }

    @Composable
    fun ErrorScreen(errorMessage: String) {
        Text(text = "Something went wrong: $errorMessage")
    }
}