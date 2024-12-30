package dev.emg.fleetio.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface ApiState<out T> {
    data class Success<T>(val data: T) : ApiState<T>
    data class Error(val exception: Throwable, val msg: String? = null) : ApiState<Nothing>
    data object Loading : ApiState<Nothing>
}

fun <T> Flow<T>.asApiState(): Flow<ApiState<T>> = map<T, ApiState<T>> { ApiState.Success(it) }
    .onStart { emit(ApiState.Loading) }
    .catch { emit(ApiState.Error(it)) }