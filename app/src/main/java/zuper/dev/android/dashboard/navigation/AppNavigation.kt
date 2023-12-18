package zuper.dev.android.dashboard.navigation

enum class Screen {
    DASHBOARD,
    JOBS,
}
sealed class NavigationItem(val route: String) {
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object Jobs : NavigationItem(Screen.JOBS.name)
}
