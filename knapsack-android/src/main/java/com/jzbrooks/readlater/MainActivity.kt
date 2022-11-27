package com.jzbrooks.readlater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jzbrooks.readlater.common.CachingEntryRepository
import com.jzbrooks.readlater.common.SettingsManager
import com.jzbrooks.readlater.common.db.DriverFactory
import com.jzbrooks.readlater.common.net.auth.Authenticator
import com.jzbrooks.readlater.common.net.entries.EntryService
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme


class MainActivity : ComponentActivity() {
    private val driverFactory = DriverFactory(this)
    private val appSettings = SettingsManager()
    private val authenticator = Authenticator(appSettings)
    private val service = EntryService(appSettings, authenticator)
    private val repository = CachingEntryRepository(authenticator, driverFactory, service)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ReadlaterTheme {
                val navController = rememberNavController()

                Scaffold(topBar = { KnapsackAppBar(navController) }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it),
                        color = MaterialTheme.colors.background
                    ) {
                        val startDestination =
                            if (authenticator.isAuthenticated) Routes.HOME else Routes.AUTH
                        NavHost(
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(Routes.AUTH) {
                                AuthScreen(appSettings, authenticator) {
                                    navController.navigate(Routes.HOME)
                                }
                            }
                            composable(Routes.HOME) {
                                ReadingListScreen(repository) { entry ->
                                    navController.navigate(Routes.READING.replace("{id}", entry.id.toString()))
                                }
                            }
                            composable(
                                Routes.READING,
                                arguments = listOf(navArgument("id") { type = NavType.LongType })
                            ) { backStackEntry -> ReadingScreen(backStackEntry, repository) }
                        }
                    }
                }
            }
        }
    }
}

object Routes {
    const val HOME = "home"
    const val AUTH = "auth"
    const val READING = "reading/{id}"
}
