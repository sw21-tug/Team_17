package com.example.loginsesame.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.loginsesame.CreateViewModel
import com.example.loginsesame.data.UserRepository

class CreateViewModelFactory(val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateViewModel(userRepository) as T
    }
}