package com.tut.mvvm_playground.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tut.mvvm_playground.databinding.RowPersonBinding
import com.tut.mvvm_playground.models.Person

class PersonListAdapter :
    PagingDataAdapter<Person, PersonListAdapter.ViewHolder>(PersonDiffUtilCallback())
{

    class ViewHolder(val binding: RowPersonBinding) :
        RecyclerView.ViewHolder(binding.root)

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<Person>() {
        override fun areItemsTheSame(
            oldItem: Person,
            newItem: Person
        ) = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Person,
            newItem: Person
        ) = oldItem.firstName == newItem.firstName && oldItem.lastName == newItem.lastName

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RowPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.person = getItem(position)
    }
}