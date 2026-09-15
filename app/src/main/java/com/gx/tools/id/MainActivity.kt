package com.gx.tools.id

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.gx.tools.id.data.Scanner
import com.gx.tools.id.ui.AppListScreen
import com.gx.tools.id.ui.theme.BgDark
import com.gx.tools.id.ui.theme.GXTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scanner = Scanner(this)

        setContent {
            GXTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(BgDark)
                ) {
                    AppListScreen(scanner)
                }
            }
        }
    }
}
