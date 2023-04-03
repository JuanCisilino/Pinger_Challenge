package com.frost.pingerchallenge.ui.main.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.frost.pingerchallenge.data.Item
import com.frost.pingerchallenge.ui.common.Progress
import com.frost.pingerchallenge.ui.main.ItemsContract

@Composable
fun MainScreen(
    context: Context?=null,
    state: ItemsContract.State,
    onEventSent: (event: ItemsContract.Event) -> Unit,
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEventSent.invoke(ItemsContract.Event.Retry) },
                backgroundColor = MaterialTheme.colors.primary,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                shape = CircleShape,
            ) {
                Icon(painterResource(id = com.frost.pingerchallenge.R.drawable.baseline_cloud_download_24),"Refresh")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        isFloatingActionButtonDocked = true,
        content = { innerPadding ->
            when {
                state.isLoading -> Progress()
                state.isError -> context?.let { Toast.makeText(it, "Error", Toast.LENGTH_LONG).show() }
                else -> {
                    val list = state.list.map { Item(it.first, it.second) }
                    LazyColumn {
                        items(list) { TwoDataInputBox(it) }
                    }
                }
            }
        }
    )
}

@Composable
fun TwoDataInputBox(item: Item) {
    Column(
        Modifier
            .padding(3.dp)
            .background(Color.LightGray)) {
        Text(
            text = if (item.count == 1)"Repeated ${item.count} time" else "Repeated ${item.count} times",
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.Black,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = item.name,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Black,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(
        state = ItemsContract.State(
            list = emptyList(),
            isLoading = false,
            isError = true,
        ),
        onEventSent = {},
    )
}