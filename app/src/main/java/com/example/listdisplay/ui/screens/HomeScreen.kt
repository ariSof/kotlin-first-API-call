/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.listdisplay.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.listdisplay.R
import com.example.listdisplay.ui.theme.ListDisplayTheme
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.listdisplay.model.ListItem
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme

@Composable
fun HomeScreen(
    listUiState: ListUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    when (listUiState) {
        is ListUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ListUiState.Success -> ListDisplayScreen(
            listUiState.items, contentPadding = contentPadding, modifier = modifier.fillMaxWidth()
            //ResultScreen(
            //marsUiState.photos, modifier = modifier.fillMaxWidth()
        )
        is ListUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}

/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

/**
 * ResultScreen displaying number of photos retrieved.
 */
@Composable
fun ResultScreen(photos: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = photos)
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    ListDisplayTheme {
        LoadingScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ListDisplayTheme {
        ErrorScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PhotosGridScreenPreview() {
    ListDisplayTheme {
        ResultScreen(stringResource(R.string.placeholder_success))
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ListDisplayGridScreenPreview() {
//    MarsPhotosTheme {
//        val mockData = List(10) { ListItem(it, 1, "Item 123") }
//        ListDisplayScreen(mockData)
//    }
//}

/**
 * The home screen displaying photo grid.
 */
@Composable
fun ListDisplayScreen(
    items: List<ListItem>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        modifier = modifier.padding(horizontal = 4.dp),
        contentPadding = contentPadding,
    ) {
        items(items = items, key = { item -> item.id }) { item ->
            Item(item)
        }
    }
}

@Composable
fun Item(item: ListItem, modifier: Modifier = Modifier) {

    Card(
        modifier = modifier.padding(6.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(30.dp),
            modifier = modifier
                .padding(20.dp)
                .fillMaxWidth()

        ) {
            item {
                Text(text = "listId = ${item.listId}",
                       // modifier = Modifier.size(40.dp)
                )
            }

            item {
                item.name?.let { Text(it) }
            }
        }
    }
}