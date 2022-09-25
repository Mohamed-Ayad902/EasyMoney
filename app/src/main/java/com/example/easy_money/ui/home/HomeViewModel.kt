package com.example.easy_money.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easy_money.R
import com.example.easy_money.models.User
import com.example.easy_money.repository.BankRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: BankRepositoryImpl) : ViewModel() {

    fun getAllUsers() = repository.getAllUsers()

    init {
        if (repository.isFirstTime())
            viewModelScope.launch {
                insertUsers()
            }
    }

    private suspend fun insertUsers() {
        repository.insertUser(
            User(
                0,
                "Mohamed Ayad",
                "xxxxxxxxx012qP",
                "mohamed.ayad7474@gmail.com",
                "Giza,Egypt",
                "01289303016",
                R.drawable.me,
                9999999
            )
        )
        repository.insertUser(
            User(
                0,
                "Sergio Ramos",
                "xxxxxxxxx044Qz",
                "sergio_r4@gmail.com",
                "Madrid,Spain",
                "01234567895",
                R.drawable.ramos,
                444444
            )
        )
        repository.insertUser(
            User(
                0,
                "Elon Musk",
                "xxxxxxxxxEf40",
                "elon_musk503@gmail.com",
                "Canada",
                "0122200111",
                R.drawable.elon,
                750000
            )
        )
        repository.insertUser(
            User(
                0,
                "David Beckham",
                "xxxxxxxxx903J",
                "beckham202@gmail.com",
                "England",
                "0174158892",
                R.drawable.beckham,
                175000
            )
        )
        repository.insertUser(
            User(
                0,
                "Cristiano Ronaldo",
                "xxxxxxxxx77SIU",
                "cr7_don@gmail.com",
                "Madeira,Portugal",
                "0777152790",
                R.drawable.ronaldo,
                777777
            )
        )
    }


}