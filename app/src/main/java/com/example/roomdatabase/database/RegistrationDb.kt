package com.example.roomdatabase.database

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.roomdatabase.dao.RegistrationDao
import com.example.roomdatabase.entity.Registration


@Database(entities = [Registration::class], version = 1,exportSchema = false)


abstract class RegistrationDb() : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: RegistrationDb? = null


        fun getDatabase(context: Context): RegistrationDb = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

         fun opened(){
            if(instance?.isOpen != true){
                instance?.openHelper?.writableDatabase
            }
        }
        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, RegistrationDb::class.java, "RegisrationDb")
                .fallbackToDestructiveMigration()
                .build()

    }

    abstract fun registrationDao(): RegistrationDao

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}