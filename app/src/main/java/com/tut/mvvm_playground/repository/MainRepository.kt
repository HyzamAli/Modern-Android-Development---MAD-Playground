package com.tut.mvvm_playground.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.network.PAGE_SIZE
import com.tut.mvvm_playground.network.PersonRemoteMediator
import com.tut.mvvm_playground.network.RetrofitInstance

const val QUERY_GET_ALL_USERS = 1

class MainRepository private constructor (private val db: AppDatabase) {
    private val retrofit = RetrofitInstance

    @ExperimentalPagingApi
    fun getPersons() = Pager(
    config = PagingConfig(PAGE_SIZE, enablePlaceholders = true),
    remoteMediator = PersonRemoteMediator(db, retrofit.getServices(), QUERY_GET_ALL_USERS)
    ) {
        db.PersonDao().getAll()
    }

    companion object {
        private var repository: MainRepository? = null

        fun getRepositoryInstance(db: AppDatabase): MainRepository {
            if (repository == null) {
                repository = MainRepository(db)
            }
            return repository!!
        }
    }
}