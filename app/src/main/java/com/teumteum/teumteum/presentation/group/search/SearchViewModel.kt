package com.teumteum.teumteum.presentation.group.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Meeting
import com.teumteum.domain.repository.GroupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: GroupRepository
) : ViewModel() {
    val etSearch = MutableStateFlow("")

    val isInputBlank
        get() = etSearch.value.isBlank()

    private var currentPage = 0

    val keywordList =
        listOf<String>("스터디", "독서", "외주", "모여서 각자 일하기", "커리어", "직무 고민", "사이드 프로젝트", "N잡")

    private val _searchData = MutableStateFlow<SearchUiState>(SearchUiState.Init)
    val searchData: StateFlow<SearchUiState> = _searchData

    fun initCurrentPage(keyword: String? = null) {
        currentPage = 0
        getSearchGroup(keyword)
    }

    fun getSearchGroup(keyword: String? = null) {
        viewModelScope.launch {
            repository.getSearchGroup(currentPage++, keyword ?: etSearch.value)
                .onSuccess {
                    if (it.isEmpty()) {
                        _searchData.value = SearchUiState.Empty(keyword ?: etSearch.value)
                    } else {
                        _searchData.value = SearchUiState.Success(it)
                    }
                }.onFailure {
                    _searchData.value = SearchUiState.Failure("실패")
                }
        }
    }
}

sealed interface SearchUiState {
    object Init : SearchUiState
    data class Empty(val keyword: String) : SearchUiState
    data class Success(val data: List<Meeting>) : SearchUiState
    data class Failure(val msg: String) : SearchUiState
}