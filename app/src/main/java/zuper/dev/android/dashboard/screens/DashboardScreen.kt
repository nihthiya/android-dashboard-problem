package zuper.dev.android.dashboard.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.common.Chart
import zuper.dev.android.dashboard.common.DefaultHeaderText
import zuper.dev.android.dashboard.common.DrawRec
import zuper.dev.android.dashboard.common.StatusColorText
import zuper.dev.android.dashboard.data.DashboardViewModel
import zuper.dev.android.dashboard.data.model.ColorCode
import zuper.dev.android.dashboard.data.model.InvoiceStatus
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus
import zuper.dev.android.dashboard.data.model.Slice


@Composable
fun DashboardScreen(
    navigateToJobsStats: () -> Unit,
    dashboardViewModel: DashboardViewModel = viewModel()
) {

    val jobs = remember {
        dashboardViewModel.tasks
    }
    val invoices = remember {
        dashboardViewModel.invoiceList
    }
    val jobSlices = remember {
        dashboardViewModel.getSlices(jobs)
    }

    val invoiceSlices = remember {
        dashboardViewModel.getInvoiceSlices(invoices)
    }
    val completedInvoiceTotal = remember {
        dashboardViewModel.getInvoiceStatusTotal(invoices, InvoiceStatus.Paid.name)
    }
    val invoiceTotal = remember {
        dashboardViewModel.getInvoiceTotal(invoices)
    }
    val draftInvoiceTotal = remember {
        dashboardViewModel.getInvoiceStatusTotal(invoices, InvoiceStatus.Draft.name)
    }
    val pendingInvoiceTotal = remember {
        dashboardViewModel.getInvoiceStatusTotal(invoices, InvoiceStatus.Pending.name)
    }
    val badDebtInvoiceTotal = remember {
        dashboardViewModel.getInvoiceStatusTotal(invoices, InvoiceStatus.BadDebt.name)
    }
    val completedCount = remember {
        dashboardViewModel.getJobsBasedOnStatus(jobs,JobStatus.Completed.name).size
    }
    val yetToStartedCount = remember {
        dashboardViewModel.getJobsBasedOnStatus(jobs,JobStatus.YetToStart.name).size
    }
    val inProgress = remember {
        dashboardViewModel.getJobsBasedOnStatus(jobs,JobStatus.InProgress.name).size
    }
    val inComplete = remember {
        dashboardViewModel.getJobsBasedOnStatus(jobs,JobStatus.Incomplete.name).size
    }
    val cancelled = remember {
        dashboardViewModel.getJobsBasedOnStatus(jobs,JobStatus.Canceled.name).size
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallTopAppBarExample(navigateToJobsStats, jobs, invoiceTotal, completedInvoiceTotal, draftInvoiceTotal, pendingInvoiceTotal, badDebtInvoiceTotal, jobSlices, invoiceSlices, completedCount, yetToStartedCount, inComplete, inProgress, cancelled)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBarExample(
    navigateToJobsStats: () -> Unit,
    jobs: List<JobApiModel>,
    totalInvoice: Int,
    completedInvoiceTotal: Int,
    draftInvoiceTotal: Int,
    pendingInvoiceTotal: Int,
    badInvoiceTotal: Int,
    slices: List<Slice>,
    invoiceSlices: List<Slice>,
    completedCount: Int,
    yetToStartedCount: Int,
    inComplete: Int,
    inProgress: Int,
    cancelled: Int
) {
    Scaffold(topBar = {
        Surface(shadowElevation = 3.dp) {
            TopAppBar(title = {
                DefaultHeaderText(text = "Dashboard", modifier = Modifier.padding(0.dp))
            })
        }
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(colorResource(id = R.color.light_white_card))
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.size(20.dp))
            ProfileCard()
            Spacer(modifier = Modifier.size(20.dp))
            JobStatsCard(jobs, slices, completedCount, yetToStartedCount, inComplete, inProgress, cancelled, navigateToJobsStats)
            Spacer(modifier = Modifier.size(20.dp))
            InvoiceStatsCard(totalInvoice, completedInvoiceTotal, draftInvoiceTotal, pendingInvoiceTotal, badInvoiceTotal, invoiceSlices)
        }
    }
}

@Composable
fun JobStatsCard(
    jobs: List<JobApiModel>,
    slices: List<Slice>,
    completedCount: Int,
    yetToStartedCount: Int,
    inComplete: Int,
    inProgress: Int,
    cancelled: Int,
    navigateToJobsStats: () -> Unit
) {
    Column {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_white),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.6.dp
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(0.1.dp, Color.LightGray),
            modifier = Modifier
                .wrapContentSize()
                .padding(18.dp, 0.dp)

        ) {
            Column {
                Button(
                    onClick = navigateToJobsStats,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent,
                        contentColor = Color.Black)

                ) {
                    Text("Job Stats", fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start)
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.6.dp)
                        .background(Color.LightGray)
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                val size = jobs.size
                Chart(slices, "$size Jobs", "$completedCount of $size completed")
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                Row (horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    DrawRec(Color( ColorCode.Violet.code), modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Yet to start ($yetToStartedCount)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))

                    DrawRec(Color( ColorCode.Blue.code), modifier = Modifier
                        .size(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "In-Progress ($inProgress)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row (horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {
                    DrawRec(Color( ColorCode.Yellow.code), modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Canceled ($cancelled)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))

                    DrawRec(Color( ColorCode.Green.code), modifier = Modifier
                        .size(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Completed ($completedCount)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row (horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {

                    DrawRec(Color( ColorCode.Red.code), modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Incomplete ($inComplete)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))
                }
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )

                }
            }
        }
    }

@Composable
fun InvoiceStatsCard(
    totalInvoice: Int,
    completedInvoiceTotal: Int,
    draftInvoiceTotal: Int,
    pendingInvoiceTotal: Int,
    badInvoiceTotal: Int,
    slices: List<Slice>
) {
    Column {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_white),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.6.dp
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(0.1.dp, Color.LightGray),
            modifier = Modifier
                .wrapContentSize()
                .padding(18.dp, 0.dp)

        ) {
                Column (verticalArrangement = Arrangement.Center,
                    modifier = Modifier.height(50.dp)){
                    Text("Invoice Stats", fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(12.dp, 0.dp))
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.6.dp)
                        .background(Color.LightGray)
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )
                Chart(slices, "Total Invoice ($$totalInvoice)", "$$completedInvoiceTotal collected")
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                Row (horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {

                    DrawRec(Color( ColorCode.Yellow.code), modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Draft ($$draftInvoiceTotal)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))

                    DrawRec(Color( ColorCode.Blue.code), modifier = Modifier
                        .size(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Pending ($$pendingInvoiceTotal)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))
                }
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                )

                Row (horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()) {

                    DrawRec(Color( ColorCode.Green.code), modifier = Modifier
                        .size(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Paid ($$completedInvoiceTotal)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))

                    DrawRec(Color( ColorCode.Red.code), modifier = Modifier
                        .width(10.dp)
                        .height(10.dp)
                        .padding(0.dp, 4.dp, 4.dp, 4.dp))
                    StatusColorText(text = "Bad Debt ($$badInvoiceTotal)",
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(6.dp, 0.dp, 6.dp, 0.dp))
                }

                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
            }
        }
    }


@Composable
fun ProfileCard() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .weight(0.1f)
        )
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = colorResource(id = R.color.light_white),
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.6.dp
            ),
            shape = RoundedCornerShape(4.dp),
            border = BorderStroke(0.1.dp, Color.LightGray),
            modifier = Modifier
                .height(80.dp)
                .weight(2f)

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "Hello, Henry",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(12.dp, 0.dp)
                    )
                    Text(
                        "Monday, Dec 18th, 2023",
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(12.dp, 0.dp)
                    )
                }

                Image(
                    painterResource(id = R.drawable.profile),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(0.dp, 12.dp)
                )
            }
        }
        Spacer(
            modifier = Modifier
                .width(10.dp)
                .weight(0.1f)
        )

    }
}



