package com.tut.mvvm_playground.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tut.mvvm_playground.models.PersonRemoteKeys
import com.tut.mvvm_playground.models.Person


@Database(entities = [Person::class, PersonRemoteKeys::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao
    abstract fun PersonRemoteKeysDao(): PersonRemoteKeysDao
}