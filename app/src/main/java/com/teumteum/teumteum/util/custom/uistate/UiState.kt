package com.teumteum.teumteum.util.custom.uistate

sealed class UiState {
    object Success : UiState()
    object Empty : UiState()
    object Failure : UiState()
    object Loading : UiState()
}
