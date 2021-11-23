package com.example.roomdatabase.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")

class Registration {
    @PrimaryKey(autoGenerate = true)
    var user_id: Int = 0
    var firstname: String = ""
    var lastname: String =""
    var age: String =""
    var mobileno: String =""
    var email: String = ""

}