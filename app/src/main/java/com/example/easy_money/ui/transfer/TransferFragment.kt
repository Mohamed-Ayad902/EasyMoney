package com.example.easy_money.ui.transfer

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.easy_money.R
import com.example.easy_money.adapters.UsersAdapter
import com.example.easy_money.databinding.FragmentTransferBinding
import com.example.easy_money.models.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "TransferFragment mohamed"

@AndroidEntryPoint
class TransferFragment : Fragment() {

    private lateinit var binding: FragmentTransferBinding
    private val args: TransferFragmentArgs by navArgs()
    private val viewModel: TransferViewModel by viewModels()
    private lateinit var usersAdapter: UsersAdapter
    var transferAmount = 0
    lateinit var sender: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTransferBinding.inflate(layoutInflater, container, false)
        binding.recyclerView.apply {
            usersAdapter = UsersAdapter(object : UsersAdapter.OnClickListener {
                override fun OnClick(user: User) {
                    showDialog(user)
                }
            })
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersAdapter
        }
        return binding.root
    }

    private fun showDialog(receiver: User) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.confirm_transaction_dialog)
        dialog.findViewById<TextView>(R.id.tv_confirmMessage).text =
            "Confirm transferring $transferAmount to ${receiver.name}?"
        val yesBtn = dialog.findViewById(R.id.btn_yes) as Button
        val noBtn = dialog.findViewById(R.id.btn_no) as TextView
        yesBtn.setOnClickListener {
            confirmTransaction(receiver, transferAmount, dialog)
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.create()
        dialog.show()
    }

    private fun confirmTransaction(receiver: User, amount: Int, dialog: Dialog) {
        viewModel.makeTransaction(sender, receiver, amount)
        dialog.dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transferAmount = args.balance
        val senderId = args.senderId

        binding.btnCancel.setOnClickListener {
            viewModel.makeTransaction(sender, null, transferAmount)
        }

        viewModel.transactionDone.collectLatest(viewLifecycleOwner) {
            if (it == "done")
                findNavController().navigate(TransferFragmentDirections.actionTransferFragmentToTransactionsFragment())
        }

        viewModel.getUsersExcept(senderId).collectLatest(viewLifecycleOwner) {
            usersAdapter.differ.submitList(it)
        }

        viewModel.getUser(senderId).collectLatest(viewLifecycleOwner) {
            sender = it
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