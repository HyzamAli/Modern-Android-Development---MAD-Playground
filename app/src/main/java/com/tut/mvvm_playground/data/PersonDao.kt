package com.tut.mvvm_playground.data

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tut.mvvm_playground.models.Person

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(persons: List<Person>)

    @Query("SELECT * FROM Person")
    fun getAll(): PagingSource<Int, Person>

    @Query("DELETE FROM Person")
    suspend fun clearPerson()
}