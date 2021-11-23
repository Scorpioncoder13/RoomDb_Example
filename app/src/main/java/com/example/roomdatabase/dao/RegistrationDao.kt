package com.example.roomdatabase.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdatabase.entity.Registration

@Dao
interface RegistrationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(registrationBean: Registration): Long

    @Query("select * from User order by User.user_id")
    suspend fun getAllUsers(): List<Registration>

    @Query("delete from User where User.user_id=:userid")
    suspend fun deleteUserIdWise(userid: Int)

    @Query("select * from User where User.user_id=:userid")
    suspend fun getDataUserIdWise(userid: Int?): Registration

    @Query("update User SET firstname=:firstname,lastname=:lastname,age=:age,mobileno =:mobileno,email=:email where user_id=:userid")
    suspend fun updateUserData(
        firstname: String,
        lastname: String,
        age: String,
        mobileno: String,
        email: String, userid: Int?
    ): Int
}
