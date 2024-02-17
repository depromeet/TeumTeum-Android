package com.teumteum.teumteum.presentation.moim

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.entity.MeetingArea
import com.teumteum.domain.entity.MoimEntity
import com.teumteum.domain.repository.GroupRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.R
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.DialogEvent
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SettingStatus
import com.teumteum.teumteum.presentation.mypage.setting.viewModel.SheetEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
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

    private val _bottomSheet = MutableStateFlow<BottomSheet>(BottomSheet.Default)
    val bottomSheet: StateFlow<BottomSheet> = _bottomSheet.asStateFlow()

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

    private val _people = MutableStateFlow(3)
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

    private val _isBookmark = MutableStateFlow(false)
    val isBookmark: StateFlow<Boolean> = _isBookmark.asStateFlow()

    private val _isAfternoon = MutableStateFlow("오후")
    val isAfternoon: StateFlow<String> = _isAfternoon.asStateFlow()

    val characterList: HashMap<Int, Int> = hashMapOf(
        0 to R.drawable.ic_ghost,
        1 to R.drawable.ic_star_character,
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
        _topic.value = null
        _title.value = ""
        _introduction.value = ""
        _imageUri.value = emptyList()
        _date.value = ""
        _time.value = ""
        _isBookmark.value = false
        _people.value = 3
        _address.value = null
        _detailAddress.value = ""
        _moinCreateUserCharacterId.value = R.drawable.ic_penguin
        _moinCreateUserName.value = ""
        _moinCreateUserJob.value = ""
        _moinJoinUsers.value = listOf()
        _meetingsId.value = 0L
        _isUserHost.value = false
    }

    fun getUserId() {
        viewModelScope.launch {
            val result = userRepository.getUserInfo()
            if (result != null) {
                _moinCreateUserJob.value = result.job?.detailClass ?: ""
                _moinCreateUserName.value = result.name.toString()
                _moinCreateUserCharacterId.value = characterList[result.characterId?.toInt() ?: 0] ?: R.drawable.ic_ghost
            }

        }
    }

    fun saveBookmark(meetingId: Long) {
        viewModelScope.launch {
            repository.saveBookmark(meetingId)
                .onSuccess {
                    _isBookmark.value = !(_isBookmark.value)
                    _screenState.value = ScreenState.BookMarkSuccess
                }
                .onFailure {
                    Timber.e(it)
                    _screenState.value = ScreenState.Server
                }
        }
    }

    fun deleteBookmark(meetingId: Long) {
        viewModelScope.launch {
            repository.deleteBookmark(meetingId)
                .onSuccess {
                    _isBookmark.value = !(_isBookmark.value)
                    _screenState.value = ScreenState.BookMarkDelete
                }
                .onFailure {
                    Timber.e(it)
                    _screenState.value = ScreenState.Server
                }
        }
    }


    fun updateTopic(topicType: TopicType) {
        _topic.value = topicType
    }

    fun updateAfternoon(string: String) {
        _isAfternoon.value = string
    }
    fun updateTitle(title: String) {
        _title.value = title
    }
    fun updateIntroduce(introduce: String) {
        _introduction.value = introduce
    }
    fun updateDate(input: String) {
        _date.value = input
    }

    fun updateTime(input: String) {
        _time.value = input
    }

    fun updateDate2(input: String) {
        if (input.length == 4) {
            _date.value = formatDateAndDay(input)
        }
    }

    fun updateTime2(input: String): String {
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

    fun updatePeople(int: Int) {
        _people.value = int
    }

    fun setMeetingId(meetingId: Long) {
        _meetingsId.value = meetingId
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
            }
        }
        _imageUri.value = currentList.take(5)
    }

    fun removeImage(uri: Uri) {
        val currentList = _imageUri.value.toMutableList()
        currentList.remove(uri)
        _imageUri.value = currentList
        Log.d("image_uri", imageUri.value.toString())
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

    private fun combineDateAndTime2(): LocalDateTime? {
        return try {
            val currentYear = Year.now().value
            val dateInput = if(_date.value.length == 4) {
                val month = _date.value.substring(0, 2)
                val day = _date.value.substring(2)
                "${currentYear}년 ${month}월 ${day}일"
            } else {
                "${currentYear}년 ${_date.value.substring(0, _date.value.lastIndexOf(" "))}"
            }
            var timeInput = _time.value.replace("오후", "PM").replace("오전", "AM")

            val timeParts = timeInput.split(" ")
            if (timeParts.size == 2) {
                val hourMinuteParts = timeParts[1].split(":")
                if (hourMinuteParts[0].length == 1) {
                    timeInput = "${timeParts[0]} 0${hourMinuteParts[0]}:${hourMinuteParts[1]}"
                }
            }

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

    private fun combineDateAndTime(): LocalDateTime? {
        return try {
            val dateInput = _date.value
            var timeInput = _time.value

            val isAfternoon = _isAfternoon.value == "오후"
            val (hourString, minuteString) = timeInput.split(":")
            var hour = hourString.toInt()
            val minute = if (minuteString.length == 1) "0$minuteString" else minuteString

            if (isAfternoon && hour < 12) { hour += 12 }
            else if (!isAfternoon && hour == 12) { hour = 0 }

            timeInput = "$hour:$minute"

            val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val timeFormatter = DateTimeFormatter.ofPattern("H:mm")

            val parsedDate = LocalDate.parse(dateInput, dateFormatter)
            val parsedTime = LocalTime.parse(timeInput, timeFormatter)

            LocalDateTime.of(parsedDate, parsedTime)
        } catch (e: Exception) {
            Timber.e(e, "Failed to combine date and time")
            null
        }
    }

    private suspend fun convertUrisToFile(uris: List<Uri>): List<File> {
        return uris.mapNotNull { uri ->
            when {
                uri.scheme?.startsWith("content") == true -> {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        val fileName = getFileName(uri, context)
                        val file = File(
                            context.cacheDir,
                            fileName ?: "tempFile-${System.currentTimeMillis()}"
                        )
                        FileOutputStream(file).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        Log.d("file", file.toString())
                        file
                    }
                }
                uri.scheme?.startsWith("http") == true ||uri.scheme?.startsWith("https") == true -> {
                    downloadFileFromUrl(uri)
                }
                else -> null
            }
        }
    }

    private suspend fun downloadFileFromUrl(uri: Uri): File? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(uri.toString())
                val connection = url.openConnection()
                connection.connect()

                val inputStream: InputStream = url.openStream()
                val file = File(context.cacheDir, "downloadedFile-${System.currentTimeMillis()}.jpg")

                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
                Log.d("file", file.toString())
                file
            } catch (e: Exception) {
                Log.e("DownloadError", "Error downloading file from $uri", e)
                null
            }
        }
    }

    fun cancelMeeting(meetingId: Long) {
        viewModelScope.launch {
            repository.deleteGroupJoin(meetingId)
                .onSuccess {
                    _screenState.value = ScreenState.CancelSuccess
                    Log.d("cancel success", screenState.value.toString())
                }
                .onFailure {
                    Timber.e(it)
                    _screenState.value = ScreenState.Server
                }
        }
    }

    fun reportMeeting(meetingId: Long) {
        viewModelScope.launch {
            repository.reportMeeting(meetingId)
                .onSuccess {
                    _screenState.value = ScreenState.ReportSuccess
                }
                .onFailure {
                    Timber.e(it)
                    _screenState.value = ScreenState.Server
                }
        }
    }

    fun deleteMeeting(meetingId: Long) {
        viewModelScope.launch {
            repository.deleteMeeting(meetingId)
                .onSuccess {
                    _isUserHost.value = false
                    _screenState.value = ScreenState.DeleteSuccess
                }
                .onFailure {
                    Timber.e(it)
                    _screenState.value = ScreenState.Server
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

    fun modifyMoim(meetingId: Long) {
        viewModelScope.launch {
            val dateTime = combineDateAndTime2()
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
                if (requestModel != null) {
                    repository.modifyMeeting(meetingId, requestModel, imageFiles)
                        .onSuccess {
                            _screenState.value = ScreenState.Success
                        }
                        .onFailure { throwable ->
                            Timber.e(throwable)
                            _screenState.value = ScreenState.Server
                        }
                }
            } else {
                _screenState.value = ScreenState.ModifyFailure
            }
        }
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
                if (requestModel != null) {
                    repository.postGroupMoim(requestModel, imageFiles)
                        .onSuccess { meeting ->
                            _screenState.value = ScreenState.Success
                        }
                        .onFailure { throwable ->
                            Timber.e(throwable)
                            _screenState.value = ScreenState.Server
                        }
                }
            } else {
                _screenState.value = ScreenState.Failure
            }
        }
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
                ScreenState.Server -> ScreenState.Create
                ScreenState.Success -> ScreenState.Success
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

    fun updateSheetEvent(screenState: ScreenState) {
        _screenState.value = screenState
    }

    private val _isUserJoined = MutableStateFlow(false)
    val isUserJoined: StateFlow<Boolean> = _isUserJoined

    private val _isUserHost = MutableStateFlow(false)
    val isUserHost: StateFlow<Boolean> = _isUserHost

    fun getGroup(meetingId: Long) {
        viewModelScope.launch {
            repository.getGroup(meetingId)
                .onSuccess {
                    Log.d("load", "load get Group")
                    getUser(it.hostId)
                    getJoinUserList(it.participantIds.joinToString { id -> id.toString() }.replace(" ", ""))

                    _isUserJoined.value = it.participantIds.contains(getMyId().toInt())
                    _isUserHost.value = it.hostId.toInt() == (getMyId().toInt())

                    _topic.value = TopicType.values().find { type ->
                        type.value == it.topic
                    }
                    _title.value = it.name
                    _introduction.value = it.introduction
                    _people.value = it.numberOfRecruits
                    _address.value = it.address
                    _detailAddress.value = it.addressDetail
                    _date.value = it.date
                    _imageUri.value = it.photoUrls.map { photos -> Uri.parse(photos) }
                    _meetingsId.value = it.id
                    _isBookmark.value = it.isBookmarked == true
                    Log.d("bookmark", it.isBookmarked.toString())
                }
        }
    }

    fun getMyId(): Long {
            return userRepository.getUserInfo()?.id ?: -1L
    }

    fun getUser(userId: Long) {
        viewModelScope.launch {
            userRepository.getUser(userId)
                .onSuccess {
                    _moinCreateUserName.value = it.name
                    _moinCreateUserJob.value = "${it.job.detailClass}"
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

    fun updateBottomSheet(bottomSheet: BottomSheet) {
        _bottomSheet.value = bottomSheet
    }

}

enum class ScreenState {
    Topic, Name, Introduce, DateTime, Address, People, Create, Success, Failure, Server,
    CancelInit, Cancel, CancelSuccess, Finish, DeleteInit, Delete, DeleteSuccess, ModifyFailure,
    Modify, Webview, ReportInit, Report, ReportSuccess, BookMarkSuccess, BookMarkDelete,
}

enum class BottomSheet {
    Topic, People, Default, Time
}


enum class TopicType(val value: String, val title: String ,val subTitle: String) {
    SHARING_WORRIES("고민_나누기", "고민 나누기", "직무,커리어 고민을 나눠보세요"),
    STUDY("스터디", "스터디", "관심 분야 스터디로 목표를 달성해요"),
    GROUP_WORK("모여서_작업", "모여서 작업", "다같이 모여서 작업해요(모각코,모각일)"),
    SIDE_PROJECT("사이드_프로젝트", "사이드 프로젝트","사이드 프로젝트로 팀을 꾸리고 성장하세요");

    companion object {
        fun fromTitle(title: String): TopicType? {
            return values().find { it.title == title }
        }
    }

}


