package zuper.dev.android.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import zuper.dev.android.dashboard.navigation.NavGraph
import zuper.dev.android.dashboard.ui.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
private fun MainScreen() {
    AppTheme {
        val navController = rememberNavController()
        NavGraph(navController)
    }
}

