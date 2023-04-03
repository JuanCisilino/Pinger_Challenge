package com.frost.pingerchallenge.ui.main

import com.frost.pingerchallenge.base.ViewEvent
import com.frost.pingerchallenge.base.ViewState

class ItemsContract {

    sealed class Event : ViewEvent {
        object Retry : Event()
        object Loaded : Event()
    }

    data class State(
        val list: List<Pair<String, Int>>,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

}