package com.teumteum.teumteum.presentation.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.JobEntity
import com.teumteum.domain.entity.UserInfo
import com.teumteum.domain.repository.AuthRepository
import com.teumteum.domain.repository.UserRepository
import com.teumteum.teumteum.util.SignupUtils.STATUS_STUDENT
import com.teumteum.teumteum.util.SignupUtils.STATUS_TRAINEE
import com.teumteum.teumteum.util.SignupUtils.STATUS_WORKER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: UserRepository,
    private val authRepository: AuthRepository
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

    fun updateCommunity(community: String) {
        _community.value = community
    }

    private val _companyName = MutableStateFlow<String>("")
    val companyName: StateFlow<String> = _companyName.asStateFlow()

    fun updateCompanyName(companyName: String) {
        _companyName.value = companyName
    }

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

    val currentJobValid: StateFlow<Boolean> = combine(
        companyName,
        jobClass,
        jobDetailClass
    ) { companyName, jobClass, jobDetailClass ->
        companyName.trim().length in 2..13 && jobClass.isNotBlank() && jobDetailClass.isNotBlank()
    }.stateIn(scope = viewModelScope, SharingStarted.Eagerly, false)

    private val _schoolName = MutableStateFlow<String>("")
    val schoolName: StateFlow<String> = _schoolName.asStateFlow()

    fun updateSchoolName(school: String) {
        _schoolName.value = school
    }

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

    fun updateInterestCount() {
        _interestCount.value = interestField.value.size + interestSelf.value.size
    }

    fun setInterestSelf(interests: List<String>) {
        _interestSelf.value = ArrayList(interests)
    }

    fun setAllInterests(interests: List<String>, selfResource: Array<String>, fieldResource: Array<String>) {
        interestSelf.value.clear()
        interestField.value.clear()
        for (i in interests) {
            if (i in selfResource) addInterestSelf(i)
            else if (i in fieldResource) addInterestField(i)
        }
    }

    private var _goalText = MutableStateFlow<String>("")
    val goalText: StateFlow<String> = _goalText.asStateFlow()

    fun updateGoalText(goal: String) {
        _goalText.value = goal
    }

    fun goToNextScreen() {
        _signUpProgress.value =
            when (_signUpProgress.value) {
                SignUpProgress.Character -> SignUpProgress.Name
                SignUpProgress.Name -> SignUpProgress.Birthday
                SignUpProgress.Birthday -> SignUpProgress.Community
                SignUpProgress.Community -> {
                    when (_community.value) {
                        STATUS_WORKER -> SignUpProgress.CurrentJob
                        STATUS_STUDENT -> SignUpProgress.School
                        STATUS_TRAINEE -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.CurrentJob -> SignUpProgress.Area
                SignUpProgress.School -> SignUpProgress.ReadyJob
                SignUpProgress.ReadyJob -> SignUpProgress.Area
                SignUpProgress.Area -> SignUpProgress.Mbti
                SignUpProgress.Mbti -> SignUpProgress.Interests
                SignUpProgress.Interests -> SignUpProgress.Goal
                SignUpProgress.Goal -> SignUpProgress.Complete
                SignUpProgress.Complete -> SignUpProgress.Fix
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
                        STATUS_STUDENT -> SignUpProgress.School
                        STATUS_TRAINEE -> SignUpProgress.Community
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Area -> {
                    when (_community.value) {
                        STATUS_WORKER -> SignUpProgress.CurrentJob
                        STATUS_STUDENT -> SignUpProgress.ReadyJob
                        STATUS_TRAINEE -> SignUpProgress.ReadyJob
                        else -> _signUpProgress.value
                    }
                }
                SignUpProgress.Mbti -> SignUpProgress.Area
                SignUpProgress.Interests -> SignUpProgress.Mbti
                SignUpProgress.Goal -> SignUpProgress.Interests
                SignUpProgress.Fix -> SignUpProgress.Complete
                else -> _signUpProgress.value
            }
        goToPreviousStep()
    }

    fun goToNextStep() {
        val isWorkerInCurrentJob = _signUpProgress.value == SignUpProgress.CurrentJob
                && _community.value == STATUS_WORKER
        val isTraineeInReadyJob = _signUpProgress.value == SignUpProgress.ReadyJob
                && _community.value == STATUS_TRAINEE
        if (isWorkerInCurrentJob || isTraineeInReadyJob) _currentStep.value++
        val nextStep = _currentStep.value + 1
        _currentStep.value = nextStep.coerceIn(1, 10)
    }

    fun goToPreviousStep() {
        val isWorkerInArea = _signUpProgress.value == SignUpProgress.Area
                && _community.value == STATUS_WORKER
        val isTraineeInArea = _signUpProgress.value == SignUpProgress.Area
                && _community.value == STATUS_TRAINEE
        if (isWorkerInArea || isTraineeInArea) _currentStep.value--
        val previousStep = _currentStep.value - 1
        _currentStep.value = previousStep.coerceAtLeast(0)
    }

    private var _userInfoState = MutableStateFlow<UserInfoUiState>(UserInfoUiState.Init)
    val userInfoState: StateFlow<UserInfoUiState> = _userInfoState.asStateFlow()

    fun postSignUp(oauthId: String, provider: String, serviceAgreed: Boolean, privatePolicyAgreed: Boolean) {
        val userInfo = getUserInfo(provider)
        if (userInfo == null) {
            _userInfoState.value = UserInfoUiState.Failure("유저 정보 만들기 실패")
        }
        else {
            viewModelScope.launch {
                repository.postUserInfo(
                    userInfo, oauthId, serviceAgreed, privatePolicyAgreed
                )
                    .onSuccess {
                        // setAutoLogin에 회원가입 이후 유저토큰 전달
                        authRepository.setAutoLogin(it.accessToken, it.refreshToken)
                        postDeviceTokens()
                        // userInfo에 임시로 넣어뒀던 아이디 -> 서버에서 받은 id 값으로 변경 -> 필요 시 사용
                        // it.id로 아이디 사용해서 내 정보 얻어오기
                        _userInfoState.value = UserInfoUiState.Success
                    }
                    .onFailure {
                        _userInfoState.value = UserInfoUiState.Failure("유저 정보 업로드 실패")
                    }
            }
        }
    }

    private fun postDeviceTokens() {
        val deviceToken = authRepository.getDeviceToken()
        if (deviceToken.isNotBlank()) {
            viewModelScope.launch {
                authRepository.postDeviceToken(deviceToken)
            }
        }
    }

    private fun combineDateStrings(): String {
        // 숫자를 두 자리로 맞추기 위해 %02d 형식을 사용
        val formattedMonth = String.format("%02d", birthMonth.value.toInt())
        val formattedDate = String.format("%02d", birthDate.value.toInt())

        // "YYYYMMDD" 형태의 문자열로 합치기
        val combinedString = "${birthYear.value.toInt()}$formattedMonth$formattedDate"

        return combinedString
    }

    private fun getUserInfo(provider: String): UserInfo? {
        val jobEntity =
            when (community.value) {
                STATUS_WORKER -> JobEntity(
                    companyName.value,
                    false,
                    jobClass.value,
                    jobDetailClass.value
                )
                STATUS_STUDENT -> JobEntity(
                    schoolName.value,
                    false,
                    readyJobClass.value,
                    readyJobDetailClass.value
                )
                STATUS_TRAINEE -> JobEntity(
                    TRAINEE_NAME,
                    false,
                    readyJobClass.value,
                    readyJobDetailClass.value
                )
                else -> null
            }

        val interests = ArrayList<String>()
        interests.addAll(interestField.value)
        interests.addAll(interestSelf.value)

        return if (jobEntity != null) {
            UserInfo(
                id = 0L,
                name = userName.value,
                birth = combineDateStrings(),
                characterId = characterId.value.toLong(),
                mannerTemperature = 36,
                authenticated = provider,
                activityArea = preferredArea.value,
                mbti =  mbtiText.value,
                status = community.value,
                goal = goalText.value,
                job = com.teumteum.domain.entity.JobEntity(jobEntity.name, false, jobEntity.jobClass, jobEntity.detailClass),
                interests = interests,
                friends = 0
            )
        } else null
    }

    companion object {
        private const val REGEX_ID_PATTERN = "^([A-Za-z0-9_.]*)\$"
        private const val TRAINEE_NAME = "준비중"
    }
}

enum class SignUpProgress {
    Character, Name, Birthday, Community, CurrentJob, School, ReadyJob, Area, Mbti, Interests, Goal,
    Complete, Fix
}

sealed interface UserInfoUiState {
    object Init: UserInfoUiState
    object Success: UserInfoUiState
    data class Failure(val msg: String): UserInfoUiState
}