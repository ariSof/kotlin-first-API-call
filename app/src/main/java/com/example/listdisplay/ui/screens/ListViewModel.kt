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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listdisplay.model.ListItem
import com.example.listdisplay.network.ListApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Home screen
 */
sealed interface ListUiState {
    data class Success(val items: List<ListItem>) : ListUiState
    object Error : ListUiState
    object Loading : ListUiState
}

class ListViewModel : ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var listUiState: ListUiState by mutableStateOf(ListUiState.Loading)
        private set

    /**
     * Call getMarsPhotos() on init so we can display status immediately.
     */
    init {
        getItemsList()
    }

    /**
     * Gets Mars photos information from the Mars API Retrofit service and updates the
     * [ListItem] [List] [MutableList].
     */
    fun getItemsList() {
        viewModelScope.launch {
            listUiState = ListUiState.Loading
            listUiState = try {
                val listResult = ListApi.retrofitService.getItems()

                val newList: List<ListItem> = listResult.filter { !it.name.isNullOrEmpty() }
                val sortedList: List<ListItem> = newList.sortedWith(compareBy<ListItem> {it.listId}.thenBy { it.name })

                ListUiState.Success( sortedList
                    //"Success: $listResult Mars photos retrieved"
                )
            } catch (e: IOException) {
                ListUiState.Error
            } catch (e: HttpException) {
                ListUiState.Error
            }
        }
    }
}


