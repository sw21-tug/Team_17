package com.example.loginsesame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserRepository
import kotlinx.coroutines.launch

class CreateViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun addUser(user: User){
        viewModelScope.launch {
            userRepository.insert(user)
        }
    }
}