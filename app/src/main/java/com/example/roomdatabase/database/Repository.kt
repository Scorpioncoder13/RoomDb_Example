package com.example.roomdatabase.database

import android.app.Application
import com.example.roomdatabase.entity.Registration

class Repository() {

    lateinit var registrationDb: RegistrationDb

    constructor(application: Application) : this() {
        registrationDb = RegistrationDb.getDatabase(application)
    }

    suspend fun addUserDb(data: Registration): Long {
        return registrationDb.registrationDao().addUser(data)
    }

    suspend fun getAllUsersData(): List<Registration> {
        return registrationDb.registrationDao().getAllUsers()
    }

    suspend fun deleteUserIdWise(id: Int) {
        return registrationDb.registrationDao().deleteUserIdWise(id)
    }

    suspend fun getDataUserIdWise(userid: Int?): Registration {
        return registrationDb.registrationDao().getDataUserIdWise(userid)
    }

    suspend fun updateuserData(
        firstname: String,
        lastname: String,
        age: String,
        mobileno: String,
        email: String,
        userid: Int
    ): Int {
        return registrationDb.registrationDao().updateUserData(
            firstname, lastname, age, mobileno,email,userid
        )
    }
}

