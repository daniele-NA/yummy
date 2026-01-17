package me.app.yummy.core.result

// == Utility for Flows,Return Types,and Callbacks == //
sealed class EmptyResultState {
    data object Idle : EmptyResultState()

    data object Loading : EmptyResultState()
    data object Success : EmptyResultState()
    data class Error(val exception: Throwable?) : EmptyResultState()

    inline fun onSuccess(block: () -> Unit): EmptyResultState {
        if (this is Success) block()
        return this
    }

    inline fun onError(block: (Throwable?) -> Unit): EmptyResultState {
        if (this is Error) block(exception)
        return this
    }
}
