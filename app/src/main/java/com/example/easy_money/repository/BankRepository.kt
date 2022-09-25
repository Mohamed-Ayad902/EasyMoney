package com.example.easy_money.repository

import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User
import kotlinx.coroutines.flow.Flow

interface BankRepository {

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    fun getAllUsers(): Flow<List<User>>

    fun getUserById(id: Int): Flow<User>

    fun getUsersExcept(id: Int): Flow<List<User>>

    suspend fun insertTransaction(transaction: Transaction)

    fun getAllTransactions(): Flow<List<Transaction>>

    fun isFirstTime(): Boolean

}