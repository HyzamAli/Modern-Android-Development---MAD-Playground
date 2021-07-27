package com.tut.mvvm_playground.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.network.PAGE_SIZE
import com.tut.mvvm_playground.network.PersonRemoteMediator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor (
    private val db: AppDatabase,
    private val personRemoteMediator: PersonRemoteMediator
    ) {

    @ExperimentalPagingApi
    fun getPersons() = Pager(
    config = PagingConfig(PAGE_SIZE, enablePlaceholders = true),
    remoteMediator = personRemoteMediator
    ) {
        db.PersonDao().getAll()
    }
}