package com.tut.mvvm_playground.viewmodel

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import com.tut.mvvm_playground.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(repository: MainRepository) : ViewModel() {

    @ExperimentalPagingApi
    val personsList =repository.getPersons().flow
}