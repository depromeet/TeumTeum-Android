package com.teumteum.teumteum.presentation.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.repository.SampleRepository
import com.teumteum.teumteum.util.custom.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    val sampleRepository: SampleRepository,
) : ViewModel() {

    private var _sampleState = MutableLiveData<UiState>(UiState.Empty)
    val sampleState: LiveData<UiState>
        get() = _sampleState

    fun getSample() {
        viewModelScope.launch {
            runCatching {
                _sampleState.value = UiState.Loading
                sampleRepository.getSample()
            }.onSuccess {
                //it.body()

                _sampleState.value = UiState.Success
            }.onFailure {
                _sampleState.value = UiState.Failure
            }
        }
    }

}





