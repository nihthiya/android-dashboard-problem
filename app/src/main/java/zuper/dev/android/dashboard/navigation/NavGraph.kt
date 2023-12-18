package zuper.dev.android.dashboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import zuper.dev.android.dashboard.screens.DashboardScreen
import zuper.dev.android.dashboard.screens.JobsScreen

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = NavigationItem.Dashboard.route
    ) {
        addDashboardScreen(navController, this)

//        addJobsScreen(navController, this, dataViewModel)

        composable(route = NavigationItem.Jobs.route) {
            JobsScreen(
                navigateToDashboard = {
                    navController.navigate(NavigationItem.Dashboard.route)
                })
        }
    }
}

private fun addDashboardScreen(
    navController: NavHostController,
    navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(route = NavigationItem.Dashboard.route) {
        DashboardScreen(
            navigateToJobsStats = {
                navController.navigate(NavigationItem.Jobs.route)
            })
    }
}