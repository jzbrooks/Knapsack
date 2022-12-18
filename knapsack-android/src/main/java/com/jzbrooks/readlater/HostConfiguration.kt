package com.jzbrooks.readlater

import android.webkit.URLUtil
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jzbrooks.readlater.common.AppSettingsManager
import com.jzbrooks.readlater.ui.theme.ReadlaterTheme

@Composable
fun HostConfiguration(
    appSettings: AppSettingsManager,
    clientId: String,
    onClientIdChanged: (String) -> Unit,
    clientSecret: String,
    onClientSecretChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
) {
    val baseUrl = rememberSaveable { mutableStateOf(appSettings.baseUrl) }

    Column(Modifier.padding(top = 8.dp).fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth(),
            Arrangement.SpaceBetween,
        ) {
            Text(
                "Host Configuration",
                style = MaterialTheme.typography.h2,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 16.dp),
            )

            IconButton(onClick = onCloseClicked) {
                Icon(Icons.Default.Close, "Close")
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
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
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.size(8.dp))

            TextField(
                clientId,
                onClientIdChanged,
                label = { Text("Client ID") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.size(8.dp))

            TextField(
                clientSecret,
                onClientSecretChanged,
                label = { Text("Client Secret") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HostConfigurationPreview() {
    ReadlaterTheme {
        HostConfiguration(
            appSettings = object : AppSettingsManager {
                override var baseUrl: String
                    get() = "https://app.wallabag.it"
                    set(_) {}
            },
            clientId = "skdfjla3nfa92qoigng2gasd",
            onClientIdChanged = { },
            clientSecret = "lkjfnin2fnalskgf8h94g",
            onClientSecretChanged = { },
            onCloseClicked = { },
        )
    }
}
