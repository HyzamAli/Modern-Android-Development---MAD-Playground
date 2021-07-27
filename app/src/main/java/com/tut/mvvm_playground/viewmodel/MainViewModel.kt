package com.tut.mvvm_playground.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.repository.MainRepository


class MainViewModel(db: AppDatabase) : ViewModel() {
    private val repository = MainRepository.getRepositoryInstance(db)

    @ExperimentalPagingApi
    val personsList =repository.getPersons().flow
}