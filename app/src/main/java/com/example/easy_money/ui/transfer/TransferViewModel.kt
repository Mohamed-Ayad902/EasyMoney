package com.example.easy_money.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easy_money.models.Transaction
import com.example.easy_money.models.User
import com.example.easy_money.repository.BankRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransferViewModel @Inject constructor(val repository: BankRepositoryImpl) : ViewModel() {

    fun getUsersExcept(id: Int) = repository.getUsersExcept(id)
    fun getUser(id: Int) = repository.getUserById(id)

    private val _transactionDone: MutableStateFlow<String> = MutableStateFlow("")
    val transactionDone: StateFlow<String> = _transactionDone

    fun makeTransaction(sender: User, receiver: User?, balance: Int) {
        viewModelScope.launch {
            if (receiver == null) {
                updateBalance(sender, null, balance)
            } else {
                updateBalance(sender, receiver, balance)
            }
        }
    }

    private suspend fun updateBalance(sender: User, receiver: User?, balance: Int) {
        if (receiver == null) {
            addToTransactions(Transaction(0, sender.name, "", false, balance))
        } else {
            addToTransactions(Transaction(0, sender.name, receiver.name, true, balance))
            sender.balance -= balance
            receiver.balance += balance
            repository.updateUser(sender)
            repository.updateUser(receiver)
            _transactionDone.value = "done"
        }

    }

    private suspend fun addToTransactions(transaction: Transaction) {
        repository.insertTransaction(transaction)
    }

}