package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.presentation.signup.UserInfoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


enum class SheetEvent {
    None, JobDetail, JobClass, Mbti, Area, Interest, Status, Dismiss,
}
@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel()
{

    init {
        loadUserInfo()
    }
    private val originalUserInfo = MutableStateFlow<UserInfo?>(null)

    private val _companyName = MutableStateFlow<String>("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    private val _jobClass = MutableStateFlow<String>("")
    val jobClass: StateFlow<String> = _jobClass.asStateFlow()

    fun updateJobClass(jobClass: String) {
        _jobClass.value = jobClass
    }

    private val _jobDetailClass = MutableStateFlow<String>("")
    val jobDetailClass: StateFlow<String> = _jobDetailClass.asStateFlow()

    fun updateJobDetailClass(jobDetailClass: String) {
        _jobDetailClass.value = jobDetailClass
    }

    fun updateCompanyName(companyName: String) {
        _companyName.value = companyName
    }
    private fun loadUserInfo() = viewModelScope.launch {
        originalUserInfo.value = userRepository.getUserInfo()
        originalUserInfo.value?.let {
            _userName.value = it.name
            _userBirth.value = formatAsDate(it.birth)
            _jobDetailClass.value = it.job.detailClass
            _jobClass.value = it.job.jobClass
            _mbtiText.value = it.mbti
            _companyName.value = it.job.name.toString()
            if (it.activityArea.length >= 2) {
                _preferredCity.value = it.activityArea.substring(0, 2)
                _preferredStreet.value = it.activityArea.substring(2)
            }
        }
    }

    fun formatAsDate(date: String): String {
        return if (date.length == 8) {
            "${date.substring(0, 4)}.${date.substring(4, 6)}.${date.substring(6, 8)}"
        } else {
            date
        }
    }

    private val _sheetEvent = MutableStateFlow(SheetEvent.None)
    val sheetEvent: StateFlow<SheetEvent> = _sheetEvent.asStateFlow()

    fun triggerSheetEvent(event: SheetEvent) {
        _sheetEvent.value = event
    }

    fun resetSheetEvent() {
        _sheetEvent.value = SheetEvent.None
    }

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _isNameValid = MutableStateFlow<Boolean>(true)
    val isNameValid: StateFlow<Boolean> = _isNameValid

    fun isValidName(name: String): Boolean {
        val invalidPattern = Regex("^[ㄱ-ㅎㅏ-ㅣ]+$")
        val isValidLength = name.length in 2..10
        val isValidKorean = name.matches(Regex("[가-힣]+"))
        return isValidLength && isValidKorean && !invalidPattern.matches(name)
    }

    fun validateUserName() {
        _isNameValid.value = isValidName(_userName.value)
    }

    fun updateUserName(name: String) {
        _userName.value = name
    }

    private val _userBirth = MutableStateFlow<String>("")
    val userBirth: StateFlow<String> = _userBirth.asStateFlow()
    fun updateUserBirth(userBirth: String) {
        _userBirth.value = userBirth
    }

    fun isValidDate(date: String): Boolean {
        val parts = date.split('.')
        if (parts.size != 3) return false

        val year = parts[0].toIntOrNull() ?: return false
        val month = parts[1].toIntOrNull() ?: return false
        val day = parts[2].toIntOrNull() ?: return false

        if (year > 2121 || month > 12 || day > 31) return false

        return true
    }

    fun formatAsDateInput(input: String): String {
        val digits = input.filter { it.isDigit() }
        return when {
            digits.length <= 4 -> digits
            digits.length <= 6 -> "${digits.substring(0, 4)}.${digits.substring(4, 6)}"
            else -> "${digits.substring(0, 4)}.${digits.substring(4, 6)}.${digits.substring(6, digits.length.coerceAtMost(8))}"
        }
    }

    private val _community = MutableStateFlow<String>("")
    val community: StateFlow<String> = _community.asStateFlow()

    fun updateCommunity(community: String) {
        _community.value = community
    }



    val currentJobValid: StateFlow<Boolean> = combine(
        companyName,
        jobClass,
        jobDetailClass
    ) { companyName, jobClass, jobDetailClass ->
        companyName.trim().length in 2..13 && jobClass.isNotBlank() && jobDetailClass.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _readyJobClass = MutableStateFlow<String>("")
    val readyJobClass: StateFlow<String> = _readyJobClass.asStateFlow()

    private val _readyJobDetailClass = MutableStateFlow<String>("")
    val readyJobDetailClass: StateFlow<String> = _readyJobDetailClass.asStateFlow()

    fun updateReadyJobClass(readyJobClass: String) {
        _readyJobClass.value = readyJobClass
    }

    fun updateReadyJobDetailClass(readyJobDetailClass: String) {
        _readyJobDetailClass.value = readyJobDetailClass
    }

    val readyJobValid: StateFlow<Boolean> = combine(
        readyJobClass,
        readyJobDetailClass
    ) { readyJobClass, readyJobDetailClass ->
        readyJobClass.isNotBlank() && readyJobDetailClass.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _preferredCity = MutableStateFlow<String>("")
    val preferredCity: StateFlow<String> = _preferredCity.asStateFlow()

    private val _preferredStreet = MutableStateFlow<String>("")
    val preferredStreet: StateFlow<String> = _preferredStreet.asStateFlow()

    fun updatePreferredArea(city: String, street: String) {
        _preferredCity.value = city
        _preferredStreet.value = street
    }

    val preferredArea: StateFlow<String> = combine(
        preferredCity,
        preferredStreet
    ) { preferredCity, preferredStreet ->
        if (preferredStreet.isNotBlank() && preferredCity.isNotBlank())
            "$preferredCity $preferredStreet"
        else ""
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, "")


    private val _mbtiText = MutableStateFlow<String>("")
    val mbtiText: StateFlow<String> = _mbtiText.asStateFlow()

    fun updateMbtiText(mbti: String) {
        _mbtiText.value = mbti
    }

    private val _interestSelf = MutableStateFlow<ArrayList<String>>(ArrayList())
    val interestSelf: StateFlow<ArrayList<String>> = _interestSelf.asStateFlow()

    fun removeInterestSelf(interest: String) {
        if (_interestSelf.value.contains(interest)) _interestSelf.value.remove(interest)
        updateInterestCount()
    }

    fun addInterestSelf(interest: String) {
        if (!_interestSelf.value.contains(interest)) _interestSelf.value.add(interest)
        updateInterestCount()
    }

    private val _interestField = MutableStateFlow<ArrayList<String>>(ArrayList())
    val interestField: StateFlow<ArrayList<String>> = _interestField.asStateFlow()

    fun removeInterestField(interest: String) {
        if (_interestField.value.contains(interest)) _interestField.value.remove(interest)
        updateInterestCount()
    }

    fun addInterestField(interest: String) {
        if (!_interestField.value.contains(interest)) _interestField.value.add(interest)
        updateInterestCount()
    }

    private var _interestCount = MutableStateFlow<Int>(0)
    val interestCount: StateFlow<Int> = _interestCount.asStateFlow()

    private fun updateInterestCount() {
        _interestCount.value = interestField.value.size + interestSelf.value.size
    }

    private var _goalText = MutableStateFlow<String>("")
    val goalText: StateFlow<String> = _goalText.asStateFlow()

    fun updateGoalText(goal: String) {
        _goalText.value = goal
    }

}