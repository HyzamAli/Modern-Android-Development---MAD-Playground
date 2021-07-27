package com.tut.mvvm_playground.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tut.mvvm_playground.models.PersonRemoteKeys

@Dao
interface PersonRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personRemoteKeys: PersonRemoteKeys)

    @Query("SELECT * FROM PersonRemoteKeys WHERE id = :id")
    fun findById(id: Int): PersonRemoteKeys

    @Query("DELETE FROM PersonRemoteKeys")
    suspend fun clearRemoteKeys()

    @Query("SELECT last_updated FROM PersonRemoteKeys LIMIT 1")
    suspend fun getLastUpdated(): Long?
}