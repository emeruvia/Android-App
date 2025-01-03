package dev.emg.fleetio.ui

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val msg: String) : UiState<Nothing>()
}