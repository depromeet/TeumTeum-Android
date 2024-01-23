package com.teumteum.teumteum.presentation.moim

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.MeetingArea
import com.teumteum.domain.entity.MoimEntity
import com.teumteum.domain.repository.GroupRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Year
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MoimViewModel @Inject constructor(
    private val repository: GroupRepository,
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.Topic)
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    private val _currentStep = MutableStateFlow<Int>(0)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _topic = MutableStateFlow<TopicType?>(null)
    val topic: StateFlow<TopicType?> = _topic.asStateFlow()

    private val _title = MutableStateFlow("")
    val title : StateFlow<String> = _title.asStateFlow()

    private val _introduction = MutableStateFlow("")
    val introduction: StateFlow<String> = _introduction.asStateFlow()

    private val _imageUri = MutableStateFlow<List<Uri>>(emptyList())
    val imageUri: StateFlow<List<Uri>> = _imageUri.asStateFlow()

    private val _date = MutableStateFlow("")
    val date : StateFlow<String> = _date.asStateFlow()

    private val _time = MutableStateFlow("")
    val time : StateFlow<String> = _time.asStateFlow()

    private val _people = MutableStateFlow(2)
    val people: StateFlow<Int> = _people.asStateFlow()

    private val _address = MutableStateFlow<String?>(null)
    val address: StateFlow<String?> = _address.asStateFlow()

    private val _detailAddress = MutableStateFlow("")
    val detailAddress: StateFlow<String> = _detailAddress

    private val _snackbarEvent = MutableSharedFlow<SnackbarEvent>()
    val snackbarEvent : SharedFlow<SnackbarEvent> = _snackbarEvent.asSharedFlow()

    private val _moinCreateUserCharacterId = MutableStateFlow(R.drawable.ic_penguin)
    val moinCreateUserCharacterId : StateFlow<Int> = _moinCreateUserCharacterId.asStateFlow()

    private val _moinCreateUserName = MutableStateFlow("")
    val moinCreateUserName : StateFlow<String> = _moinCreateUserName.asStateFlow()

    private val _moinCreateUserJob = MutableStateFlow("")
    val moinCreateUserJob : StateFlow<String> = _moinCreateUserJob.asStateFlow()

    private val _moinJoinUsers = MutableStateFlow<List<Friend>>(listOf())
    val moimJoinUsers : StateFlow<List<Friend>> = _moinJoinUsers.asStateFlow()

    private val _meetingsId = MutableStateFlow<Long>(0L)
    val meetingsId : StateFlow<Long> = _meetingsId.asStateFlow()

    val characterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_ghost,
        1 to R.drawable.ic_star,
        2 to R.drawable.ic_bear,
        3 to R.drawable.ic_raccoon,
        4 to R.drawable.ic_cat,
        5 to R.drawable.ic_rabbit,
        6 to R.drawable.ic_fox,
        7 to R.drawable.ic_water,
        8 to R.drawable.ic_penguin,
        9 to R.drawable.ic_dog,
        10 to R.drawable.ic_mouse,
        11 to R.drawable.ic_panda
    )

    fun initializeState() {
        _screenState.value = ScreenState.Topic
        _currentStep.value = 0
    }

    fun updateTopic(topicType: TopicType) {
        _topic.value = topicType
    }
    fun updateTitle(title: String) {
        _title.value = title
    }
    fun updateIntroduce(introduce: String) {
        _introduction.value = introduce
    }
    fun updateDate(input: String) {
        if (input.length == 4) {
            _date.value = formatDateAndDay(input)
        }
    }

    fun updateTime(input: String): String {
        val formattedTime = formatTime(input)
        _time.value = formattedTime
        return formattedTime
    }

    fun updateAddress(address: String) {
        _address.value = address
    }
    fun updateDetailAddress(address: String) {
        _detailAddress.value = address
    }

    fun upPeople() {
        if(_people.value < 6) { _people.value +=1 }
    }
    fun downPeople() {
        if(_people.value > 1) { _people.value -=1 }
    }
    fun resetSnackbarEvent() {
        viewModelScope.launch {
            _snackbarEvent.emit(SnackbarEvent.DEFAULT)
        }
    }
    fun addImages(uris: List<Uri>, context: Context) {
        val currentList = _imageUri.value.toMutableList()
        var isOverSizeImageFound = false

        for (uri in uris) {
            val size = getFileSize(uri, context)
            if (size > 10 * 1024 * 1024) { // 10MB 이상인 경우
                isOverSizeImageFound = true
            } else if (currentList.size < 5) { // 사이즈가 적절하고, 현재 리스트 크기가 5 미만인 경우
                currentList.add(uri)
            }
        }
        if (isOverSizeImageFound) {
            viewModelScope.launch {
                _snackbarEvent.emit(SnackbarEvent.FILE_OVER_10MB)
                Log.d("snackbarEvent", _snackbarEvent.toString())
            }
        }
        _imageUri.value = currentList.take(5)
    }


    fun formatTime(input: String): String {
        return try {
            val parsedTime = LocalTime.parse(input, DateTimeFormatter.ofPattern("HHmm"))
            parsedTime.format(DateTimeFormatter.ofPattern("a hh:mm", Locale.KOREA))
        } catch (e: Exception) {
            input // 실패시 원본 입력 반환
        }
    }

    fun formatDateAndDay(input: String): String {
        return try {
            val currentYear = Year.now().value
            val parsedDate = LocalDate.parse("$currentYear$input", DateTimeFormatter.ofPattern("yyyyMMdd"))
            val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("MM월 dd일"))
            val dayOfWeek = parsedDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN)
            "$formattedDate $dayOfWeek"
        } catch (e: Exception) {
            input // 실패시 원본 입력 반환
        }
    }

    private fun combineDateAndTime(): LocalDateTime? {
        return try {
            val currentYear = Year.now().value
            val dateInput = "${currentYear}년 ${_date.value.substring(0, _date.value.lastIndexOf(" "))}"
            val timeInput = _time.value.replace("오후", "PM").replace("오전", "AM")

            val dateFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일", Locale.KOREAN)
            val timeFormatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.ENGLISH)

            val parsedDate = LocalDate.parse(dateInput, dateFormatter)
            val parsedTime = LocalTime.parse(timeInput, timeFormatter)

            LocalDateTime.of(parsedDate, parsedTime)
        } catch (e: Exception) {
            Timber.e(e, "Failed to combine date and time")
            null
        }
    }

    private fun convertUrisToFile(uris: List<Uri>): List<File> {
        return uris.mapNotNull { uri ->
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val fileName = getFileName(uri, context)
                val file = File(context.cacheDir, fileName ?: "tempFile-${System.currentTimeMillis()}")
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                file
            }
        }
    }

    // Uri로부터 파일 이름을 추출하는 함수
    private fun getFileName(uri: Uri, context: Context): String? {
        var name: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            // 컬럼 인덱스 유효성 확인
            if (nameIndex != -1 && it.moveToFirst()) {
                name = it.getString(nameIndex)
            }
        }
        return name
    }

    fun createMoim() {
        viewModelScope.launch {
            val dateTime = combineDateAndTime()
            val imageFiles = convertUrisToFile(imageUri.value)
            if(dateTime != null) {
                val meetingArea = address.value?.let {
                    MeetingArea(
                        address = it,
                        addressDetail = detailAddress.value
                    )
                }
                val requestModel = topic.value?.let {
                    if (meetingArea != null) {
                        MoimEntity(
                            topic = it.value,
                            title = title.value,
                            introduction = introduction.value,
                            promiseDateTime = dateTime,
                            numberOfRecruits = people.value,
                            meetingArea = meetingArea
                        )
                    } else { null }
                }
                Log.d("requestModel", requestModel.toString())
                if (requestModel != null) {
                    repository.postGroupMoim(requestModel, imageFiles)
                        .onSuccess { meeting ->
                            Log.d("createMoim", "success")
                            _screenState.value = ScreenState.Success
                            Log.d("screenState_success", screenState.value.toString())
                        }
                        .onFailure { throwable ->
                            Log.d("createMoim", "failure")
                            Timber.e(throwable)
                            _screenState.value = ScreenState.Server
                        }
                }
            } else {
                Log.d("createMoim", "time is null")
                _screenState.value = ScreenState.Failure
                Log.d("sceenState after", _screenState.toString())
            }
        }
    }

    fun resetScreenState() {
        _screenState.value = ScreenState.Topic
        _currentStep.value = 0
    }

    fun goToNextScreen() {
        _screenState.value =
            when(_screenState.value) {
                ScreenState.Topic -> ScreenState.Name
                ScreenState.Name -> ScreenState.Introduce
                ScreenState.Introduce -> ScreenState.DateTime
                ScreenState.DateTime -> ScreenState.Address
                ScreenState.Address -> ScreenState.People
                ScreenState.People -> ScreenState.Create
                ScreenState.Create -> ScreenState.Success
                else -> _screenState.value
            }
        goToNextStep()
    }

    fun goPreviousScreen() {
        _screenState.value =
            when(_screenState.value) {
                ScreenState.Failure -> ScreenState.Create
                ScreenState.Create -> ScreenState.Address
                ScreenState.Address -> ScreenState.DateTime
                ScreenState.DateTime -> ScreenState.Introduce
                ScreenState.Introduce -> ScreenState.Name
                ScreenState.Name -> ScreenState.Topic
                else -> _screenState.value
            }
        goToPreviousStep()
    }

    fun goToNextStep() {
        val nextStep = _currentStep.value + 1
        _currentStep.value = nextStep.coerceIn(0, 5)
    }

    fun goToPreviousStep() {
        val previousStep = _currentStep.value - 1
        _currentStep.value = previousStep.coerceAtLeast(0)
    }

    fun getFileSize(uri: Uri, context: Context): Long {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        val sizeIndex = cursor?.getColumnIndex(OpenableColumns.SIZE)
        cursor?.moveToFirst()
        val size = sizeIndex?.let { cursor.getLong(it) } ?: 0L
        cursor?.close()
        return size
    }

    fun getTopicTitle(topicType: TopicType?): String {
        return topicType?.title ?: ""
    }

    enum class SnackbarEvent {
        DEFAULT, FILE_OVER_10MB;
        fun getMessage(): String {
            return when (this) {
                FILE_OVER_10MB -> "10mb 이하의 사진을 등록해주세요"
                else -> ""
            }
        }
    }

    fun getGroup(meetingId: Long) {
        viewModelScope.launch {
            repository.getGroup(meetingId)
                .onSuccess {
                    getUser(it.hostId)
                    getJoinUserList(it.participantIds.joinToString { id -> id.toString() }.replace(" ", ""))

                    _topic.value = TopicType.values().find { type ->
                        type.value == it.topic
                    }
                    _introduction.value = it.introduction
                    _people.value = it.numberOfRecruits
                    _address.value = it.address
                    _detailAddress.value = it.addressDetail
                    _date.value = it.date
                    _imageUri.value = it.photoUrls.map { photos -> Uri.parse(photos) }
                    _meetingsId.value = it.id
                }
        }
    }

    private fun getUser(userId: Long) {
        viewModelScope.launch {
            userRepository.getUser(userId)
                .onSuccess {
                    _moinCreateUserName.value = it.name
                    _moinCreateUserJob.value = "${it.job.detailClass} ${it.job.jobClass}"
                    _moinCreateUserCharacterId.value = characterList[it.characterId] ?: R.drawable.ic_penguin
                }
        }
    }

    private fun getJoinUserList(id: String) {
        viewModelScope.launch {
            userRepository.getUsers(id)
                .onSuccess {
                    _moinJoinUsers.value = it.users
                }
        }
    }
}

enum class ScreenState {
    Topic, Name, Introduce, DateTime, Address, People, Create, Success, Failure, Server
}
enum class TopicType(val value: String, val title: String ,val subTitle: String) {
    SHARING_WORRIES("고민_나누기", "고민 나누기", "직무,커리어 고민을 나눠보세요"),
    STUDY("스터디", "스터디", "관심 분야 스터디로 목표를 달성해요"),
    GROUP_WORK("모여서_작업", "모여서 작업", "다같이 모여서 작업해요(모각코,모각일)"),
    SIDE_PROJECT("사이드_프로젝트", "사이드 프로젝트","사이드 프로젝트로 팀을 꾸리고 성장하세요")

}