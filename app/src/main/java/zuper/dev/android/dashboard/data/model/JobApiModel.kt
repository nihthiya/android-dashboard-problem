package zuper.dev.android.dashboard.data.model

import android.graphics.Color


/**
 * A simple API model representing a Job
 *
 * Start and end date time is in ISO 8601 format - Date and time are stored in UTC timezone and
 * expected to be shown on the UI in the local timezone
 */
data class JobApiModel(
    val jobNumber: Int,
    val title: String,
    val startTime: String,
    val endTime: String,
    val status: JobStatus
)

enum class JobStatus {
    Completed,
    InProgress,
    YetToStart,
    Canceled,
    Incomplete
}

enum class ColorCode(val code: Int) {
    Green(Color.parseColor("#73CAA4")),
    Blue(Color.parseColor("#80D4FA")),
    Violet(Color.parseColor("#9EA8DA")),
    Yellow(Color.parseColor("#FCCD57")),
    Red(Color.parseColor("#E57273"))
}

enum class TabItems {
    YetToStart,
    InProgress,
    Canceled
}

data class Slice(val value: Int, val color: Int, val status: String = "")

