package dev.emg.fleetio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.emg.fleetio.R
import dev.emg.fleetio.network.models.filterByOptions
import dev.emg.fleetio.network.models.sortByOptions
import dev.emg.fleetio.ui.vehicles.VehiclesViewModel
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBarComponent(
    modifier: Modifier = Modifier,
    viewModel: VehiclesViewModel
) {
    Column {
        TopAppBar(
            title = { Text(text = "Fleetio", fontSize = 26.sp, fontWeight = FontWeight.Bold) },
            modifier = modifier.fillMaxWidth()
        )

        val filterSheetState = rememberModalBottomSheetState()
        var showFilterBottomSheet by remember { mutableStateOf(false) }

        val sortSheetState = rememberModalBottomSheetState()
        var showSortBottomSheet by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier.padding(start = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AssistChip(
                onClick = { showFilterBottomSheet = true },
                label = { Text(text = "Filter") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.filter_list),
                        contentDescription = "filter icon",
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
            AssistChip(
                onClick = { showSortBottomSheet = true },
                label = { Text(text = "Sort") },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.sort_icon),
                        contentDescription = "filter icon",
                        modifier = Modifier.size(AssistChipDefaults.IconSize)
                    )
                }
            )
        }

        if (showFilterBottomSheet) {
            FilterBottomSheet(sheetState = filterSheetState, viewModel = viewModel, onDismiss = {
                showFilterBottomSheet = false
            })
        }

        if (showSortBottomSheet) {
            SortBottomSheet(sheetState = sortSheetState, viewModel = viewModel, onDismiss = {
                showSortBottomSheet = false
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    viewModel: VehiclesViewModel,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        modifier = modifier
    ) {
        val filterState = viewModel.filterByState.collectAsState()
        val filterByOptions = filterByOptions()

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Text(
                text = "filter by",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = modifier.fillMaxWidth()
            )
            filterByOptions.forEach { (filterBy, name) ->
                val isSelected = filterState.value == filterBy
                ListItemComponent(name = name, isSelected = isSelected) {
                    viewModel.setFilterByOption(filterBy)
                }
            }
            Spacer(modifier = Modifier.padding(all = 16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    viewModel: VehiclesViewModel,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        modifier = modifier
    ) {
        val sortState = viewModel.sortByState.collectAsState()
        val sortByOptions = sortByOptions()

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Text(
                text = "sort by",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = modifier.fillMaxWidth()
            )
            sortByOptions.forEach { (sortBy, name) ->
                val isSelected = sortState.value == sortBy
                ListItemComponent(name = name, isSelected = isSelected) {
                    viewModel.setSortByOption(sortBy)
                }
            }
            Spacer(modifier = Modifier.padding(all = 16.dp))
        }
    }
}