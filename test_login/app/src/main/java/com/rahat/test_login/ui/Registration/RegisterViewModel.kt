package com.rahat.test_login.ui.Registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahat.test_login.repository.FirestoreRepository
import com.rahat.test_login.data.User
import com.rahat.test_login.utils.Utils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()
    private val registerViewModelEventChannel = Channel<RegisterEvent>()
    val registerEvent = registerViewModelEventChannel.receiveAsFlow()

    // save user
    private fun saveUser(user: User) {
        // update the ui the work is happening
        viewModelScope.launch {

            firebaseRepository.saveUser(user)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        registerViewModelEventChannel.send(RegisterEvent.ShowProgressBar(false))
                        registerViewModelEventChannel.send(RegisterEvent.RegisterDone(true))
                        registerViewModelEventChannel.send(RegisterEvent.ShowErrorMessage("Registration Done , Please Login To Continue"))
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        registerViewModelEventChannel.send(RegisterEvent.ShowProgressBar(false))
                        registerViewModelEventChannel.send(RegisterEvent.ShowErrorMessage("Error : ${it.localizedMessage}"))
                    }
                }
        }
    }

    // for checking user data
    //will perform a basic validation
    fun checkUserData(user: User) = viewModelScope.launch {
        if (user.username.isNotEmpty() && user.password.isNotEmpty() && Utils.isValidEmail(user.email)) {
            // all the data is valid
            // trigger
            registerViewModelEventChannel.send(RegisterEvent.ShowProgressBar(true))
            isEmailExist(user)

        } else {
            // update the ui via event
            registerViewModelEventChannel.send(RegisterEvent.ShowErrorMessage("Please Check The Entered Data !!!"))
        }

    }

    // perform a check if a user already exist with the same email address
    private fun isEmailExist(user: User) {

        firebaseRepository.searchUserByEmail(user.email)
            .addOnSuccessListener { userList ->
                if (userList.isEmpty) {
                    //   user has a unique mail carry on
                    saveUser(user)
                } else {
                    viewModelScope.launch {
                        registerViewModelEventChannel.send(RegisterEvent.ShowProgressBar(false))
                        registerViewModelEventChannel.send(RegisterEvent.ShowErrorMessage("Error : User with same email Exist in the database"))
                    }
                }

            }
            .addOnFailureListener { exception ->
                viewModelScope.launch {
                    registerViewModelEventChannel.send(RegisterEvent.ShowErrorMessage("Error : Try again !! ${exception.localizedMessage}"))
                }
            }


    }


    // for send event in the fragment
    sealed class RegisterEvent {
        data class ShowErrorMessage(val msg: String) : RegisterEvent()
        data class ShowProgressBar(val show: Boolean) : RegisterEvent()
        data class RegisterDone(val isDone: Boolean) : RegisterEvent()

    }
}