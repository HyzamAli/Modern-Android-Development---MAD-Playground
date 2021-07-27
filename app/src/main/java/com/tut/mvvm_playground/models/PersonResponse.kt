package com.tut.mvvm_playground.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Person(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    val avatar: String,

    @ColumnInfo(name = "first_name")
    @SerializedName("first_name")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    val lastName: String,

    @ColumnInfo(name = "email")
    @SerializedName("email")
    val email: String
)

/**
 * This is a helper entity to track the next page numbers for different queries associated
 * with the Person remote object
 *
 * Eg: Row with id = 1, represents page data currently available related to a query to fetch
 * all user data
 */
@Entity
data class PersonRemoteKeys(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "next_key")
    val nextKey: Int?,
)