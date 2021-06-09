package com.example.loginsesame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loginsesame.data.UserRepository

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users = userRepository.users.asLiveData()
    val entries = userRepository.entries.asLiveData()
}
