package com.jzbrooks.readlater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jzbrooks.readlater.data.SettingsManager
import com.jzbrooks.readlater.data.CachingEntryRepository
import com.jzbrooks.readlater.data.db.DriverFactory
import com.jzbrooks.readlater.data.net.auth.Authenticator
import com.jzbrooks.readlater.data.net.entries.EntryService
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


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
                                LaunchedEffect(null) {
                                    repository.updateEntries()
                                }

                                ReadingListScreen(repository) { entry ->
                                    navController.navigate(Routes.READING.removeSuffix("{content}") + URLEncoder.encode(entry.content, StandardCharsets.UTF_8.name()))
                                }
                            }
                            composable(Routes.READING) { backStackEntry ->
                                ReadingScreen(content = URLDecoder.decode(backStackEntry.arguments?.getString("content")!!, StandardCharsets.UTF_8.name()))
                            }
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
    const val READING = "reading/{content}"
}
