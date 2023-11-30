package com.apex.codeassesment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apex.codeassesment.data.UserRepo
import com.apex.codeassesment.data.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO (3 points): Add tests for viewmodel or presenter.
class UserViewModel @Inject constructor(
    private val repository: UserRepo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _randomUser = MutableLiveData<User>()
    val randomUser: LiveData<User> get() = _randomUser

    init {
        viewModelScope.launch(dispatcher) {
            refreshUsers()
            _randomUser.value = repository.getSavedUser()
        }
    }

    fun refreshUsers() {
        viewModelScope.launch(dispatcher) {
            _users.value = repository.getUsers()
        }
    }

    fun refreshRandomUser() {
        viewModelScope.launch(dispatcher) {
            val randomUser = repository.getUser(true)
            _randomUser.value = randomUser
        }
    }

    fun getSavedUser() = repository.getSavedUser()
}