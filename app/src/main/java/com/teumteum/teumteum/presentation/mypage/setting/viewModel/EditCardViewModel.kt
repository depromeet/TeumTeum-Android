package com.teumteum.teumteum.presentation.mypage.setting.viewModel

import android.util.Log
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


enum class SheetEvent {
    None, JobDetail, JobClass, Mbti, Area, Status, Dismiss, SignUp, Error, Success
}
@HiltViewModel
class EditCardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel()
{

    init {
        loadUserInfo()
    }


    private val _sheetEvent = MutableStateFlow(SheetEvent.None)
    val sheetEvent: StateFlow<SheetEvent> = _sheetEvent.asStateFlow()

    fun triggerSheetEvent(event: SheetEvent) {
        _sheetEvent.value = event
    }
    fun loadUserInfo() = viewModelScope.launch {
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
            _goalText.value = it.goal
            _community.value = it.status
            _interestField.value = it.interests as ArrayList<String>
        }
    }

    fun isUserInfoChanged(): Boolean {
        originalUserInfo.value?.let { original ->
            val formattedBirth = _userBirth.value.replace(".", "")
            val currentInfo = original.copy(
                name = _userName.value,
                birth = formattedBirth,
                status =  _community.value,
                interests =  _interestField.value,
                mbti = _mbtiText.value,
                job = original.job.copy(
                    name = _companyName.value,
                    jobClass = _jobClass.value,
                    detailClass = _jobDetailClass.value
                ),
                activityArea = "${_preferredCity.value} ${_preferredStreet.value}",
                goal = _goalText.value
            )
            return currentInfo != original
        }
        return false
    }



    fun updateUserInfo() = viewModelScope.launch {
        if(isUserInfoChanged()) {
            originalUserInfo.value?.let { original->
                val updatedUserInfo = original.copy(
                    name = _userName.value,
                    birth = _userBirth.value,
                    status = _community.value,
                    mbti =  _mbtiText.value,
                    interests = _interestField.value,
                    job = original.job.copy(
                        name = _companyName.value,
                        jobClass = _jobClass.value,
                        detailClass = _jobDetailClass.value
                    ),
                    activityArea = "${_preferredCity.value} ${_preferredStreet.value}",
                    goal = _goalText.value

                )
                userRepository.updateUserInfo(updatedUserInfo)
                    .onSuccess {
                        Log.d("업데이트 성공", "성공")
                        saveDatastore(updatedUserInfo)
                        _sheetEvent.value = SheetEvent.Success
                    }
                    .onFailure {
                       _sheetEvent.value = SheetEvent.Error
                        Timber.e(it)
                    }
            }
        }
    }

    private fun saveDatastore(userInfo: UserInfo) = viewModelScope.launch {
        userRepository.saveUserInfo(userInfo)
    }

    private val originalUserInfo = MutableStateFlow<UserInfo?>(null)

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _isNameValid = MutableStateFlow<Boolean>(true)
    val isNameValid: StateFlow<Boolean> = _isNameValid

    fun isValidName(name: String): Boolean {
        val nameWithoutSpaces = name.filter { !it.isWhitespace() }
        val invalidPattern = Regex("^[ㄱ-ㅎㅏ-ㅣ]+$")
        val isValidLength = nameWithoutSpaces.length in 2..10
        val containsNoAlphabet = name.none { it in 'a'..'z' || it in 'A'..'Z' } // 알파벳이 없어야 함
        return isValidLength && containsNoAlphabet && !invalidPattern.matches(name)
    }

    fun updateUserName(name: String) {
        _isNameValid.value = isValidName(_userName.value)
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

    fun formatAsDate(date: String): String {
        return if (date.length == 8) {
            "${date.substring(0, 4)}.${date.substring(4, 6)}.${date.substring(6, 8)}"
        } else {
            date
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

    private val _interestField = MutableStateFlow<ArrayList<String>>(ArrayList())
    val interestField: StateFlow<ArrayList<String>> = _interestField.asStateFlow()

    fun removeInterestField(interest: String) {
        val updatedInterests = _interestField.value.toMutableList().apply {
            remove(interest)
        }
        _interestField.value = updatedInterests as ArrayList<String>
        updateInterestCount()
    }

    fun setInterestField(interests: ArrayList<String>) {
        _interestField.value = interests
        updateInterestCount()
    }

    private var _interestCount = MutableStateFlow<Int>(0)
    val interestCount: StateFlow<Int> = _interestCount.asStateFlow()

    private fun updateInterestCount() {
        _interestCount.value = interestField.value.size
    }

    private var _goalText = MutableStateFlow<String>("")
    val goalText: StateFlow<String> = _goalText.asStateFlow()

    fun updateGoalText(goal: String) {
        _goalText.value = goal
    }




}