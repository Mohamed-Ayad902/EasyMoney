package com.example.easy_money.ui.transactions

import androidx.lifecycle.ViewModel
import com.example.easy_money.repository.BankRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(val repository: BankRepositoryImpl) : ViewModel() {

    fun getAllTransactions() = repository.getAllTransactions()

}