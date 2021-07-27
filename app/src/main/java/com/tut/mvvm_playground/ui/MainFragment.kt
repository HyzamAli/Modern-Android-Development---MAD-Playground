package com.tut.mvvm_playground.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.tut.mvvm_playground.data.AppDatabase
import com.tut.mvvm_playground.databinding.FragmentMainBinding
import com.tut.mvvm_playground.network.NetworkResponseHandler
import com.tut.mvvm_playground.viewmodel.MainViewModel
import com.tut.mvvm_playground.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private lateinit var personAdapter: PersonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModelFactory = MainViewModelFactory(AppDatabase.getDbInstance(requireContext()))
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        return binding.root
    }

    @ExperimentalPagingApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        personAdapter = PersonListAdapter()
        binding.personList.adapter = personAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            personAdapter.loadStateFlow.collectLatest { loadState ->
                binding.progressbar.isVisible = loadState.refresh is LoadState.Loading
                val errorState = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }

                errorState?.let {
                    val message =
                        NetworkResponseHandler.handleResponse<Any>(errorState.error).message
                    Snackbar.make(
                        binding.root, "${message?:"Try Again Later"}",
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.personsList
                .collectLatest { pagingData ->
                    personAdapter.submitData(pagingData)
                }
        }
    }

}