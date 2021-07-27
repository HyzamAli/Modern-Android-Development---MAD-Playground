package com.tut.mvvm_playground.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.network.PAGE_SIZE
import com.tut.mvvm_playground.network.PersonApi
import com.tut.mvvm_playground.network.PersonRemoteMediator
import javax.inject.Inject
import javax.inject.Singleton

const val QUERY_GET_ALL_USERS = 1

@Singleton
class MainRepository @Inject constructor (
    private val db: AppDatabase,
    private val personApi: PersonApi
    ) {

    @ExperimentalPagingApi
    fun getPersons() = Pager(
    config = PagingConfig(PAGE_SIZE, enablePlaceholders = true),
    remoteMediator = PersonRemoteMediator(db, personApi, QUERY_GET_ALL_USERS)
    ) {
        db.PersonDao().getAll()
    }
}