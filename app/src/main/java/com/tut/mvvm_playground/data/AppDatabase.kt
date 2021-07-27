package com.tut.mvvm_playground.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tut.mvvm_playground.models.PersonRemoteKeys
import com.tut.mvvm_playground.models.Person


@Database(entities = [Person::class, PersonRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao
    abstract fun PersonRemoteKeysDao(): PersonRemoteKeysDao

    companion object {
        private var db: AppDatabase? = null
        private const val db_name = "person-database"

        fun getDbInstance(context: Context): AppDatabase {
            if (db == null) {
                synchronized(AppDatabase::class) {
                    if (db == null) {
                        db = Room.databaseBuilder(
                            context,
                            AppDatabase::class.java, db_name
                        ).build()
                    }
                }
            }
            return db!!
        }
    }
}