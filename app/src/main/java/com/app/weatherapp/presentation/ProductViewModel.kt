package com.app.weatherapp.presentation

import MainUiEvents
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weatherapp.data.ProductRepo
import com.app.weatherapp.data.Result
import com.app.weatherapp.data.model.product
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(
    private val productRepo: ProductRepo,
): ViewModel() {

    private val _products = MutableStateFlow<product?>(null)
    val product = _products.asStateFlow()

    private val _showErrorToastChannel = Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    private var searchJob: Job? = null


    init { //default city is toronto
        _mainState.update { it.copy(searchWord = "Toronto") }
        loadWeatherData()
    }

    fun onEvent(mainUiEvents: MainUiEvents) {
        if (mainUiEvents == MainUiEvents.OnSearchClick) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                loadWeatherData()

            }
        }
        else if (mainUiEvents is MainUiEvents.OnSeacrchWordChange) {
            _mainState.update {
                it.copy(searchWord = mainUiEvents.newWord.lowercase())
            }
        }
    }

    private fun loadWeatherData() {
        viewModelScope.launch {
            productRepo.getProductList(mainState.value.searchWord).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {
                        result.data?.let { product ->
                            _products.update { product }

                        }
                    }
                }

            }
        }
    }
}

