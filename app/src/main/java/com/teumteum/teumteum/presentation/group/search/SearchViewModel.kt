package com.teumteum.teumteum.presentation.group.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Group
import com.teumteum.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GroupRepository
): ViewModel() {
    val etSearch = MutableStateFlow("")

    val isInputBlank = etSearch.value.isBlank()

    val keywordList = listOf<String>("스터디", "독서", "외주", "모여서 각자 일하기", "커리어", "직무 고민", "사이드 프로젝트", "N잡")

    private val _searchData = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val searchData: StateFlow<SearchUiState> = _searchData

    fun getSearchGroup(keyword: String) {
        viewModelScope.launch {
            repository.getSearchGroup(keyword)
                .onSuccess {
                    if (it.isEmpty()) {
                        _searchData.value = SearchUiState.Failure("실패")
                    } else {
                        _searchData.value = SearchUiState.Success(it)
                    }
                }
        }
    }

}

sealed interface SearchUiState {
    object Init: SearchUiState
    data class Success(val data: List<Group>): SearchUiState
    data class Failure(val msg: String): SearchUiState
}