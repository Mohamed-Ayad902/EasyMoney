package com.example.easy_money.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User

@Database(entities = [User::class, Transaction::class], version = 1)
abstract class BankDatabase : RoomDatabase() {
    abstract fun dao(): BankDao
}