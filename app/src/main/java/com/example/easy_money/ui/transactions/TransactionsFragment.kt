package com.example.easy_money.ui.transactions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easy_money.adapters.TransactionsAdapter
import com.example.easy_money.databinding.FragmentTransactionsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionsFragment : Fragment() {

    private lateinit var binding: FragmentTransactionsBinding
    private lateinit var transactionsAdapter: TransactionsAdapter
    private val viewModel: TransactionsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransactionsBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.apply {
            transactionsAdapter = TransactionsAdapter()
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionsAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllTransactions().collectLatest(viewLifecycleOwner) {
            Log.d("mohamed", "onViewCreated: $it")
            transactionsAdapter.differ.submitList(it)
            it.forEach { transaction -> Log.d("mohamed", "onViewCreated: $transaction\n") }
        }

    }


}

inline fun <T> Flow<T>.collectLatest(
    owner: LifecycleOwner,
    crossinline onCollect: suspend (T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        collectLatest {
            onCollect(it)
        }
    }
}