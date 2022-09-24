package com.example.easy_money.repository

import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User
import kotlinx.coroutines.flow.Flow

interface BankRepository {

    suspend fun insertUser(user: User)

    fun getAllUsers(): Flow<List<User>>

    suspend fun insertTransaction(transaction: Transaction)

    fun getAllTransactions(): Flow<List<Transaction>>

    fun isFirstTime():Boolean

}