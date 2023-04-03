package com.frost.pingerchallenge.ui.main

import androidx.lifecycle.viewModelScope
import com.frost.pingerchallenge.base.BaseViewModel
import com.frost.pingerchallenge.network.FetchUC
import com.frost.pingerchallenge.ui.common.ApacheLogParser
import com.frost.pingerchallenge.ui.common.PageSequenceCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val fetchUC: FetchUC):
    BaseViewModel<ItemsContract.Event, ItemsContract.State>(){

    private val numberOfConsecutivePages = 3
    private val pageSequenceCalculator = PageSequenceCalculator()

    init {
        handleEvents(ItemsContract.Event.Retry)
    }

    override fun setInitialState() = ItemsContract.State(
        list = listOf(),
        isLoading = true,
        isError = false
    )

    override fun handleEvents(event: ItemsContract.Event) {
        when (event) {
            ItemsContract.Event.Retry -> getAll()
            else -> {}
        }
    }

    private fun getAll() {
        setState { copy(list = listOf(), isLoading = true, isError = false) }
        viewModelScope.launch {
            fetchUC.fetchStream()?.collect {
                val logs = ApacheLogParser(it).parseLogsForEachUser()
                val pathSequenceList = calculateMostPopularPathSequences(logs)
                setState { copy(list = pathSequenceList, isLoading = false, isError = false) }
            }?: setState { copy(list = listOf(), isLoading = false, isError = true) }
        }
    }

    private fun calculateMostPopularPathSequences(parsedLogs: HashMap<String, MutableList<String>>) =
        pageSequenceCalculator.getMostCommonPageSequences(parsedLogs, numberOfConsecutivePages)

}