package com.example.gazbizceevee

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gazbizceevee.network.APICeeVeeDeserializer
import com.example.gazbizceevee.network.Experience
import com.example.gazbizceevee.ui.Screen
import com.example.gazbizceevee.ui.theme.GazBizCeeVeeTheme
import com.example.gazbizceevee.utils.FileUtils
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState

class MainActivity : ComponentActivity() {

    private val fileUtils = FileUtils()
    private val deserializer = APICeeVeeDeserializer()
    private val ceeVee by lazy { deserializer.getCeeVee() }

    private val navigationItems = listOf(
        Screen.Home,
        Screen.Experience,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { CreateBottomNav(navController) }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Home.route) { Home() }
                    composable(Screen.Experience.route) { Experience() }
                }
            }
        }
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
            //todo appears to be a bug with this when re-creating? Dig in and flag on gitHub
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(30.dp),
            modifier = Modifier.width(IntrinsicSize.Min)
        ) {
            items(ceeVee.experience) { experience ->
                ExperienceRow(experience)
            }
        }
    }

    @Composable
    private fun ExperienceRow(experience: Experience) {
        ElevatedCard() {
            Text(text = experience.companyName, fontSize = 30.sp)
        }
    }

    @Composable
    fun ErrorScreen(errorMessage: String) {
        Text(text = "Something went wrong: $errorMessage")
    }
}