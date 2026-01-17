package me.app.yummy.core.result

// == Util for Flows,Return Types,and Callbacks == //
sealed class ResultState<out T> {
    data object Idle : ResultState<Nothing>()
    data object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Throwable) : ResultState<Nothing>()

    inline fun onSuccess(block: (T) -> Unit): ResultState<T> {
        if (this is Success) block(data)
        return this
    }

    inline fun onError(block: (Throwable) -> Unit): ResultState<T> {
        if (this is Error) block(exception)
        return this
    }
}