package com.teumteum.teumteum.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
//    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _signUpProgress = MutableStateFlow<SignUpProgress>(SignUpProgress.Character)
    val signUpProgress: StateFlow<SignUpProgress> = _signUpProgress.asStateFlow()

    private val _currentStep = MutableStateFlow<Int>(1)
    val currentStep: StateFlow<Int> = _currentStep.asStateFlow()

    private val _characterId = MutableStateFlow<Int>(-1)
    val characterId: StateFlow<Int> = _characterId.asStateFlow()

    fun updateCharacterId(characterId: Int) {
        _characterId.value = characterId
    }

    private val _userName = MutableStateFlow<String>("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    fun updateUserName(userName: String) {
        _userName.value = userName
    }

    private val _birthYear = MutableStateFlow("")
    val birthYear: StateFlow<String> = _birthYear.asStateFlow()
    private val _birthMonth = MutableStateFlow("")
    val birthMonth: StateFlow<String> = _birthMonth.asStateFlow()
    private val _birthDate = MutableStateFlow("")
    val birthDate: StateFlow<String> = _birthDate.asStateFlow()
    val birthValid: StateFlow<Boolean> = combine(
        birthYear,
        birthMonth,
        birthDate
    ) { year, month, date ->
        year.toIntOrNull() in 1000..2122
                && month.toIntOrNull() in 1..12
                && date.toIntOrNull() in 1..31
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    fun updateBirthYear(birthYear: String) {
        _birthYear.value = birthYear
    }

    fun updateBirthMonth(birthMonth: String) {
        _birthMonth.value = birthMonth
    }

    fun updateBirthDate(birthDate: String) {
        _birthDate.value = birthDate
    }

    private val _community = MutableStateFlow<String>("")
    val community: StateFlow<String> = _community.asStateFlow()

    private val _companyName = MutableStateFlow<String>("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    private val _jobClass = MutableStateFlow<String>("")
    val jobClass: StateFlow<String> = _jobClass.asStateFlow()

    private val _jobDetailClass = MutableStateFlow<String>("")
    val jobDetailClass: StateFlow<String> = _jobDetailClass.asStateFlow()

    private val _preferredArea = MutableStateFlow<String>("")
    val preferredArea: StateFlow<String> = _preferredArea.asStateFlow()

    private val _mbtiText = MutableStateFlow<String>("")
    val mbtiText: StateFlow<String> = _mbtiText.asStateFlow()

    fun goToNextScreen() {
        _signUpProgress.value =
            when (_signUpProgress.value) {
                SignUpProgress.Character -> SignUpProgress.Name
                SignUpProgress.Name -> SignUpProgress.Birthday
                SignUpProgress.Birthday -> SignUpProgress.Community
                SignUpProgress.Community -> {
                    when (_community.value) {
                        "직장인" -> SignUpProgress.CurrentJob
                        "학생" -> SignUpProgress.School
                        "취업준비생" -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.CurrentJob -> SignUpProgress.Area
                SignUpProgress.School -> SignUpProgress.ReadyJob
                SignUpProgress.ReadyJob -> SignUpProgress.Area
                SignUpProgress.Area -> SignUpProgress.Mbti
                SignUpProgress.Mbti -> SignUpProgress.Interests
                SignUpProgress.Interests -> SignUpProgress.Goal
                else -> _signUpProgress.value
            }
        goToNextStep()
    }

    fun goToPreviousScreen() {
        _signUpProgress.value =
            when (_signUpProgress.value) {
                SignUpProgress.Name -> SignUpProgress.Character
                SignUpProgress.Birthday -> SignUpProgress.Name
                SignUpProgress.Community -> SignUpProgress.Birthday
                SignUpProgress.CurrentJob -> SignUpProgress.Community
                SignUpProgress.School -> SignUpProgress.Community
                SignUpProgress.ReadyJob -> {
                    when (_community.value) {
                        "학생" -> SignUpProgress.School
                        "취업준비생" -> SignUpProgress.Community
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Area -> {
                    when (_community.value) {
                        "직장인" -> SignUpProgress.CurrentJob
                        "학생" -> SignUpProgress.ReadyJob
                        "취업준비생" -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Mbti -> SignUpProgress.Area
                SignUpProgress.Interests -> SignUpProgress.Mbti
                SignUpProgress.Goal -> SignUpProgress.Interests
                else -> _signUpProgress.value
            }
        goToPreviousStep()
    }

    fun goToNextStep() {
        if (_signUpProgress.value == SignUpProgress.CurrentJob && _community.value == "직장인"
            || _signUpProgress.value == SignUpProgress.ReadyJob && _community.value == "취업준비생")
            _currentStep.value++
        val nextStep = _currentStep.value + 1
        _currentStep.value = nextStep.coerceIn(1, 10)
    }

    fun goToPreviousStep() {
        if (_signUpProgress.value == SignUpProgress.Area && _community.value == "직장인"
            || _signUpProgress.value == SignUpProgress.Area && _community.value == "취업준비생")
            _currentStep.value--
        val previousStep = _currentStep.value - 1
        _currentStep.value = previousStep.coerceAtLeast(0)
    }

    companion object {
        private const val REGEX_ID_PATTERN = "^([A-Za-z0-9_.]*)\$"
    }
}

enum class SignUpProgress {
    Character, Name, Birthday, Community, CurrentJob, School, ReadyJob, Area, Mbti, Interests, Goal
}