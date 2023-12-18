package zuper.dev.android.dashboard.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import zuper.dev.android.dashboard.R
import zuper.dev.android.dashboard.common.Chart
import zuper.dev.android.dashboard.common.DefaultHeaderText
import zuper.dev.android.dashboard.data.JobsViewModel
import zuper.dev.android.dashboard.data.model.JobApiModel
import zuper.dev.android.dashboard.data.model.JobStatus

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun JobsScreen(
    navigateToDashboard: () -> Unit,
    jobsViewModel: JobsViewModel = viewModel()
) {
    val jobs = remember {
        jobsViewModel.tasks
    }
    val tabItems = remember {
        jobsViewModel.tabs
    }
    val slices = remember {
        jobsViewModel.getSlices(jobs)
    }
    val completedCount = remember {
        jobsViewModel.getJobsBasedOnStatus(jobs, JobStatus.Completed.name).size
    }


    Scaffold(topBar = {
        Surface(shadowElevation = 3.dp) {
            TopAppBar(title = {
                DefaultHeaderText(text = "Jobs", modifier = Modifier)
            },
                navigationIcon = {
                    IconButton(onClick = navigateToDashboard
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                })
        }
    }) { innerPadding ->
        Surface (modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
            color = MaterialTheme.colorScheme.background) {
            var selectedTabIndex by remember {
                mutableIntStateOf(0)
            }

            val pagerState = rememberPagerState {
                tabItems.size
            }

            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }
            }
            Column (modifier = Modifier.fillMaxSize()) {
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                val size = jobs.size
                Chart(slices, "$size Jobs", "$completedCount of $size completed")
                Spacer(
                    modifier = Modifier
                        .height(20.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(0.6.dp)
                        .background(Color.LightGray)
                )
                TabRow(selectedTabIndex = selectedTabIndex) {

                    tabItems.forEachIndexed { index, item ->
                        Tab(selected = index == selectedTabIndex,
                            onClick = {
                                selectedTabIndex = index
                            },
                            text = {
                                Text(text = item.name,
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            })
                    }
                }
                HorizontalPager(state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth())
//                    .weight(1f))
                { index ->
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        items(
                            items = jobsViewModel.getJobsBasedOnStatus(jobs, tabItems[index].name),
                            itemContent = {
                                UpdateView(it)
                            })
                    }
//                DefaultHeaderText(text = tabItems.get(index).name,
//                    modifier = Modifier)
                }

            }
        }
    }


//        DefaultButton(
//            text = "Back",
//            onClick = navigateToDashboard
//        )
//    }
}

@Composable
fun UpdateView(job: JobApiModel) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.light_white),
        ),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        ),
//        border = BorderStroke(0.4.dp, Color.LightGray),
        modifier = Modifier
            .wrapContentSize()
            .fillMaxWidth()

    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
            Column {
                Spacer(modifier = Modifier
                    .height(10.dp))
                Text(
                    "#" + job.jobNumber.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(12.dp, 0.dp)
                )
                Text(
                    job.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(12.dp, 4.dp, 0.dp, 0.dp)
                )
                Text(
                    job.startTime,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(12.dp, 4.dp, 0.dp, 0.dp)
                )
                Spacer(modifier = Modifier
                    .height(10.dp))
            }
//        }
    }
//    Row {
//        Column {
//            Text(text = job.title, color = Color.Black)
//            Text(text = job.status.name, color = Color.Black)
//        }
//    } 9384474329
}

