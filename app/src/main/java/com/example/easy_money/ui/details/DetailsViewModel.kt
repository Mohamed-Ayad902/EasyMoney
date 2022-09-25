package com.example.easy_money.ui.details

import androidx.lifecycle.ViewModel
import com.example.easy_money.repository.BankRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(val repository: BankRepositoryImpl) : ViewModel() {

    fun getUser(id: Int) = repository.getUserById(id)


}