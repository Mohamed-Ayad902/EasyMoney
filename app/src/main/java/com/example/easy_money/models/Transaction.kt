package com.example.easy_money.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val senderName: String,
    val receiverName: String,
    val completed: Boolean,
    val balance: Int,
)
