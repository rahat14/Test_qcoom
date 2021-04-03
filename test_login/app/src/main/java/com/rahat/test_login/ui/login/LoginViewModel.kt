package com.rahat.test_login.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahat.test_login.repository.FirestoreRepository
import com.rahat.test_login.data.User
import com.rahat.test_login.utils.SharedPrefManager
import com.rahat.test_login.utils.Utils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val TAG = "LOGIN_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()
    private val registerViewModelEventChannel = Channel<LoginEvent>()
    val loginEvent = registerViewModelEventChannel.receiveAsFlow()


    fun loginUser(email: String, pass: String) = viewModelScope.launch {

        if (email.isNotEmpty() && pass.isNotEmpty()) {
            /*
             going for login
             */
            registerViewModelEventChannel.send(LoginEvent.ShowProgressBar(true))

            firebaseRepository.loginUser(email, pass).addOnSuccessListener { userList ->
                // so here we have the list of the same email and pass as user entered
                if (!userList.isEmpty) {
                    // as there only can be one user  in the list if its more than that then something went wrong
                    viewModelScope.launch {
                        if (userList.size() != 1) {
                            /*  someThing  went wrong
                                there is more than one user with same email and pass
                             */
                            registerViewModelEventChannel.send(LoginEvent.ShowErrorMessage("Error :-> Double"))

                        } else {
                            // now we get model of the user from the list of 1
                             val user : User

                            for (doc in userList!!) {
                                user = doc.toObject(User::class.java)
                                SharedPrefManager.put( user , Utils.USER_PREF )
                                break
                            }
                            registerViewModelEventChannel.send(LoginEvent.LoginDone(true))

                        }
                    }

                }
                else {
                    viewModelScope.launch {
                        registerViewModelEventChannel.send(LoginEvent.ShowProgressBar(false))
                        registerViewModelEventChannel.send(LoginEvent.ShowErrorMessage("Error : User with this email Does not Exist in the database"))
                    }
                }

            }.addOnFailureListener { exception ->
                viewModelScope.launch {
                    registerViewModelEventChannel.send(LoginEvent.ShowErrorMessage("Error : Try again !! ${exception.localizedMessage}"))
                }
            }

        } else {
            /*
             provided data invalid update the ui
             */
            registerViewModelEventChannel.send(LoginEvent.ShowErrorMessage("Error : Invalid Data !!"))

        }
    }

    sealed class LoginEvent {
        data class ShowErrorMessage(val msg: String) : LoginEvent()
        data class ShowProgressBar(val show: Boolean) : LoginEvent()
        data class LoginDone(val isDone: Boolean) : LoginEvent()

    }
}