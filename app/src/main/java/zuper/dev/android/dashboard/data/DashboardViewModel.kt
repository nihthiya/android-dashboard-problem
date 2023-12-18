package zuper.dev.android.dashboard.data

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import zuper.dev.android.dashboard.data.model.ColorCode
import zuper.dev.android.dashboard.data.model.InvoiceApiModel
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.data.model.Slice

class DashboardViewModel : ViewModel() {
    private val respository = DataRepository()
    private var _tasks: List<JobApiModel>
    private var _invoiceList: List<InvoiceApiModel>

    init {
        _tasks = getJobsList().toMutableStateList()
        _invoiceList = getInvoicesList().toMutableStateList()
    }

    val tasks: List<JobApiModel>
        get() = _tasks
    val invoiceList: List<InvoiceApiModel>
        get() = _invoiceList


    fun getSlices(jobs: List<JobApiModel>): List<Slice> {
        val slice = mutableListOf<Slice>()
        enumValues<JobStatus>().toList().forEach {
            slice.add(
                Slice(
                    getJobsBasedOnStatus(jobs, it.name).size,
                    getStatusColour(it.name),
                    it.name
                )
            )
        }
        return slice
    }

    fun getInvoiceSlices(invoice: List<InvoiceApiModel>): List<Slice> {
        val slice = mutableListOf<Slice>()
        enumValues<InvoiceStatus>().toList().forEach {
            slice.add(
                Slice(
                    getInvoiceBasedOnStatus(invoice, it.name).size,
                    getInvoiceStatusColour(it.name),
                    it.name
                )
            )
        }
        return slice
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

    private fun getInvoicesList(): List<InvoiceApiModel> {
        var invoices: List<InvoiceApiModel> = mutableListOf()

        viewModelScope.launch {
            respository.observeInvoices().collect {
                invoices = it
            }
        }
        return invoices
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

    private fun getInvoiceStatusColour(status: String): Int {
        return when (status) {
            InvoiceStatus.Draft.name -> ColorCode.Yellow.code
            InvoiceStatus.Pending.name -> ColorCode.Blue.code
            InvoiceStatus.Paid.name -> ColorCode.Green.code
            InvoiceStatus.BadDebt.name -> ColorCode.Red.code
            else -> {
                0
            }
        }
    }

    val getJobsBasedOnStatus =
        { jobs: List<JobApiModel>, status: String -> jobs.filter { it.status.name == status } }

    fun getInvoiceStatusTotal(invoice: List<InvoiceApiModel>, status: String): Int {
        var total = 0
        invoice.forEach {
            if (it.status.name == status) {
                total += it.total
            }
        }
        return total
    }

    fun getInvoiceTotal(invoice: List<InvoiceApiModel>): Int {
        var total = 0
        invoice.forEach {
            total += it.total

        }
        return total
    }


    val getInvoiceBasedOnStatus =
        { jobs: List<InvoiceApiModel>, status: String -> jobs.filter { it.status.name == status } }
}