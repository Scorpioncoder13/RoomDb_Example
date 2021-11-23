package com.example.roomdatabase.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomdatabase.database.Repository
import com.example.roomdatabase.entity.Registration

class UserListingViewModel() : ViewModel() {
    lateinit var repository: Repository

    constructor(application: Application) : this() {
        repository = Repository(application)
    }

    suspend fun getUserList():List<Registration> {
        return repository.getAllUsersData()
    }
    suspend fun deleteRecordUserIdWise(id:Int){
        repository.deleteUserIdWise(id)
    }


}