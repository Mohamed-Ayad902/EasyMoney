package com.example.easy_money.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface BankDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("select * from users_table")
    fun getAllUsers(): Flow<List<User>>

    @Query("select * from users_table where id !=:id")
    fun getUsersExcept(id: Int): Flow<List<User>>

    @Query("select * from users_table where id =:id")
    fun getUserById(id: Int): Flow<User>

    @Insert
    suspend fun insertTransaction(transaction: Transaction)

    @Query("select * from transaction_table")
    fun getAllTransactions(): Flow<List<Transaction>>


}