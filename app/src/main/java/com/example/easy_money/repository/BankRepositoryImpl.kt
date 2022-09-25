package com.example.easy_money.repository

import com.example.easy_money.db.BankDao
import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User
import com.example.easy_money.others.SharedPreferenceHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val sp: SharedPreferenceHelper,
    private val dao: BankDao
) : BankRepository {

    override suspend fun insertUser(user: User) = dao.insertUser(user)

    override suspend fun updateUser(user: User) = dao.updateUser(user)

    override fun getAllUsers(): Flow<List<User>> = dao.getAllUsers()

    override fun getUserById(id: Int) = dao.getUserById(id)

    override fun getUsersExcept(id: Int): Flow<List<User>> = dao.getUsersExcept(id)

    override suspend fun insertTransaction(transaction: Transaction) =
        dao.insertTransaction(transaction)

    override fun getAllTransactions(): Flow<List<Transaction>> = dao.getAllTransactions()

    override fun isFirstTime() = sp.isFirstTime()

}