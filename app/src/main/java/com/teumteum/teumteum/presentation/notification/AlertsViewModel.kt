package com.teumteum.teumteum.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.TeumAlert
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _alertsData = MutableStateFlow<AlertsListUiState>(AlertsListUiState.Init)
    val alertsData: StateFlow<AlertsListUiState> = _alertsData

    fun getAlerts() {
        viewModelScope.launch {
            repository.getAlerts()
                .onSuccess {
                    if (it.alerts.isEmpty()) _alertsData.value = AlertsListUiState.Empty
                    else _alertsData.value = AlertsListUiState.SetAlerts(it.alerts)
                }
                .onFailure {
                    _alertsData.value = AlertsListUiState.Failure("알림 가져오기 실패")
                }
        }
    }
}

sealed interface AlertsListUiState {
    object Init : AlertsListUiState
    object Empty : AlertsListUiState
    data class SetAlerts(val data: List<TeumAlert>) : AlertsListUiState
    data class Failure(val msg: String) : AlertsListUiState
}