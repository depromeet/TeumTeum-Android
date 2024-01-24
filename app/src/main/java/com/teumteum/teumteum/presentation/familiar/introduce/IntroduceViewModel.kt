package com.teumteum.teumteum.presentation.familiar.introduce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teumteum.domain.entity.Friend
import com.teumteum.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroduceViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _introduceUser = MutableLiveData<MutableList<Friend>>()
    val introduceUser: LiveData<MutableList<Friend>>
        get() = _introduceUser

    fun getIntroduceUser(id: String) {
        viewModelScope.launch {
            userRepository.getUsers(id = id)
                .onSuccess {
                    _introduceUser.value = it.users.toMutableList()
                }
        }
    }
}





