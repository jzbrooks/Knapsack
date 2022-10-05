package com.jzbrooks.readlater

import android.webkit.URLUtil
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jzbrooks.readlater.data.AppSettingsManager
import com.jzbrooks.readlater.data.net.auth.AuthenticationManager
import com.jzbrooks.readlater.data.net.auth.PasswordGrantRequestDto
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AuthScreen(
    appSettings: AppSettingsManager,
    authenticationManager: AuthenticationManager,
    onSignIn: () -> Unit,
) {
    val username = rememberSaveable { mutableStateOf(BuildConfig.CONST_USERNAME) }
    val password = rememberSaveable { mutableStateOf(BuildConfig.CONST_PASSWORD) }
    val clientId = rememberSaveable { mutableStateOf(BuildConfig.CONST_CLIENT_ID) }
    val clientSecret = rememberSaveable { mutableStateOf(BuildConfig.CONST_CLIENT_SECRET) }

    val coroutineScope = rememberCoroutineScope()

    val bottomSheet = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
    )

    ModalBottomSheetLayout(
        sheetContent = {
            val baseUrl = rememberSaveable { mutableStateOf(appSettings.baseUrl) }

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    TextField(
                        baseUrl.value,
                        {
                            baseUrl.value = it
                            if (URLUtil.isValidUrl(it)) {
                                appSettings.baseUrl = it
                            }
                        },
                        label = { Text("Base Url") },
                        singleLine = true,
                    )

                    Spacer(Modifier.size(8.dp))

                    TextField(
                        clientId.value,
                        { clientId.value = it },
                        label = { Text("Client ID") },
                        singleLine = true,
                    )

                    Spacer(Modifier.size(8.dp))

                    TextField(
                        clientSecret.value,
                        { clientSecret.value = it },
                        label = { Text("Client Secret") },
                        singleLine = true,
                    )
                }
            }
        },
        sheetState = bottomSheet,
    ) {
        Column(Modifier.background(MaterialTheme.colors.secondary)) {
            IconButton(onClick = { coroutineScope.launch { bottomSheet.show() } }) {
                Icon(Icons.Default.Settings, "Settings")
            }

            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    TextField(
                        username.value,
                        { username.value = it },
                        label = { Text("Name") },
                        singleLine = true,
                    )

                    Spacer(Modifier.size(8.dp))

                    TextField(
                        password.value,
                        { password.value = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                    )

                    Spacer(Modifier.size(8.dp))

                    Button(
                        onClick = {
                            val grantRequestDto = PasswordGrantRequestDto(
                                clientId.value,
                                clientSecret.value,
                                username.value,
                                password.value
                            )

                            coroutineScope.launch {
                                authenticationManager.authenticate(grantRequestDto)
                                onSignIn()
                            }
                        },
                    ) {
                        Text("Sign In")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    ReadlaterTheme {
        AuthScreen(
            appSettings = AppSettingsManager(),
            authenticationManager = object : AuthenticationManager {
                override val isAuthenticated: Boolean = false
                override suspend fun retrieveAccessToken(): String = ""
                override suspend fun authenticate(password: PasswordGrantRequestDto) { }
                override fun deleteCredentials() { }
            }
        ) { }
    }
}