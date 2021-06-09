package com.example.loginsesame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.loginsesame.data.User
import com.example.loginsesame.data.UserRepository
import com.example.loginsesame.data.VaultEntry
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    val users = userRepository.users.asLiveData()
    val entries = userRepository.entries.asLiveData()

    fun deleteVaultEntry(vaultEntry: VaultEntry){
        GlobalScope.launch {
            userRepository.deleteVaultEntry(vaultEntry)
        }
    }
}
