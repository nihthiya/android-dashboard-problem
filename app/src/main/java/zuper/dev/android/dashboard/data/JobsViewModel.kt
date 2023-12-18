package zuper.dev.android.dashboard.data

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.model.ColorCode
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.data.model.Slice
import zuper.dev.android.dashboard.data.model.TabItems

class JobsViewModel : ViewModel() {

    private val respository = DataRepository()
    private var _tasks: List<JobApiModel>
    private var _tabs: List<TabItems>

    init {
        _tasks = getJobsList().toMutableStateList()
        _tabs = getTabItems().toMutableStateList()
    }

    val tasks: List<JobApiModel>
        get() = _tasks
    val tabs: List<TabItems>
        get() = _tabs

    private fun getTabItems(): List<TabItems> {
        var myList: List<TabItems> = mutableListOf()

        viewModelScope.launch {

            myList = respository.getTabItems()
        }
        return myList
    }

    private fun getJobsList(): List<JobApiModel> {
        var jobsList: List<JobApiModel> = mutableListOf()

        viewModelScope.launch {
            respository.observeJobs().collect {
                jobsList = it
            }
        }
        return jobsList
    }

    val getJobsBasedOnStatus =
        { jobs: List<JobApiModel>, status: String -> jobs.filter { it.status.name == status } }

    fun getSlices(jobs: List<JobApiModel>): List<Slice> {
        val slice = mutableListOf<Slice>()
        enumValues<JobStatus>().toList().forEach {
            slice.add(
                Slice(
                    getJobsBasedOnStatus(jobs, it.name).size, getStatusColour(it.name), it.name
                )
            )
        }
        return slice
    }

    private fun getStatusColour(status: String): Int {
        return when (status) {
            JobStatus.Completed.name -> ColorCode.Green.code
            JobStatus.YetToStart.name -> ColorCode.Violet.code
            JobStatus.Canceled.name -> ColorCode.Yellow.code
            JobStatus.InProgress.name -> ColorCode.Blue.code
            JobStatus.Incomplete.name -> ColorCode.Red.code
            else -> {
                0
            }
        }
    }
}