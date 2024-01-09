package com.teumteum.teumteum.presentation.moim

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(

): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Topic)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _currentStep = MutableStateFlow<Int>(1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _topic = MutableStateFlow<TopicType?>(null)
    val topic: StateFlow<TopicType?> = _topic.asStateFlow()

    private val _title = MutableStateFlow("")
    val title : StateFlow<String> = _title.asStateFlow()

    private val _introduction = MutableStateFlow("")
    val introduction: StateFlow<String> = _introduction.asStateFlow()

    private val _imageUri = MutableStateFlow<List<Uri>>(emptyList())
    val imageUri: StateFlow<List<Uri>> = _imageUri.asStateFlow()

    fun updateTopic(topicType: TopicType) {
        _topic.value = topicType
    }

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateIntroduce(introduce: String) {
        _introduction.value = introduce
    }

    fun addImages(uris: List<Uri>, context: Context) {
        val currentList = _imageUri.value.toMutableList()
        for (uri in uris) {
            val size = getFileSize(uri, context)
            if (size <= 10 * 1024 * 1024 && currentList.size < 5) { // 10MB 이하이고, 현재 리스트 크기가 5 미만인 경우
                currentList.add(uri)
            }
        }
        _imageUri.value = currentList.take(5)
    }
    fun goToNextScreen() {
        _screenState.value =
            when(_screenState.value) {
                ScreenState.Topic -> ScreenState.Name
                ScreenState.Name -> ScreenState.Introduce
                ScreenState.Introduce -> ScreenState.DateTime
                ScreenState.DateTime -> ScreenState.Address
                else -> ScreenState.Create
            }
        goToNextStep()
        Log.d("currentStep", _currentStep.value.toString())
    }

    fun goPreviousScreen() {
        _screenState.value =
            when(_screenState.value) {
                ScreenState.Create -> ScreenState.Address
                ScreenState.Address -> ScreenState.DateTime
                ScreenState.DateTime -> ScreenState.Introduce
                ScreenState.Introduce -> ScreenState.Name
                else -> ScreenState.Topic
            }
        goToPreviousStep()
    }

    fun goToNextStep() {
        val nextStep = _currentStep.value + 1
        _currentStep.value = nextStep.coerceIn(1, 6)
    }

    fun goToPreviousStep() {
        val previousStep = _currentStep.value - 1
        _currentStep.value = previousStep.coerceAtLeast(1)
    }

    fun getFileSize(uri: Uri, context: Context): Long {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)
        cursor?.moveToFirst()
        val size = sizeIndex?.let { cursor.getLong(it) } ?: 0L
        cursor?.close()
        return size
    }




}

enum class TopicType(val value: String, val title: String ,val subTitle: String) {
    SHARING_WORRIES("고민_나누기", "고민 나누기", "직무,커리어 고민을 나눠보세요"),
    STUDY("스터디", "스터디", "관심 분야 스터디로 목표를 달성해요"),
    GROUP_WORK("모여서_작업", "모여서 작업", "다같이 모여서 작업해요(모각코,모각일)"),
    SIDE_PROJECT("사이드_프로젝트", "사이드 프로젝트","사이드 프로젝트로 팀을 꾸리고 성장하세요")

}

enum class ScreenState {
    Topic, Name, Introduce, DateTime, Address, People, Create
}