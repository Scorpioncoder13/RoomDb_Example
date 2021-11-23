package com.example.roomdatabase.viewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.database.Repository
import com.example.roomdatabase.entity.Registration
import kotlinx.coroutines.launch

class RegistrationFormViewModel() : ViewModel() {
    lateinit var repository: Repository

    constructor(application: Application) : this() {
        repository = Repository(application)
    }

    val firstname = MutableLiveData<String>()
    val lastname = MutableLiveData<String>()
    val age = MutableLiveData<String>()
    val mobileno = MutableLiveData<String>()
    val emailid = MutableLiveData<String>()


    suspend fun addUserData() {
        viewModelScope.launch {
            var registration: Registration = Registration()
            registration.firstname = firstname.value.toString()
            registration.lastname = lastname.value.toString()
            registration.age = age.value.toString()
            registration.mobileno = mobileno.value.toString()
            registration.email = emailid.value.toString()
            repository.addUserDb(registration)
        }
    }

    suspend fun getDataForUpadate(userid: Int?): Registration {
        return repository.getDataUserIdWise(userid)

    }

    suspend fun updateUserData(userid: Int) {
        repository.updateuserData(
            firstname.value.toString(),
            lastname.value.toString(),
            age.value.toString(),
            mobileno.value.toString(),
            emailid.value.toString(),
            userid
        )
    }
}