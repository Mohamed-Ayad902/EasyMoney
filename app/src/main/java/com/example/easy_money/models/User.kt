package com.example.easy_money.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val code: String,
    val email: String,
    val address: String,
    val phone: String,
    val image: Int,
    var balance: Int,
)
