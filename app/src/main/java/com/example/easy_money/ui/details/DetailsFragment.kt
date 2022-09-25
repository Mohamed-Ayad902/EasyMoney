package com.example.easy_money.ui.details

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.easy_money.R
import com.example.easy_money.databinding.FragmentDetailsBinding
import com.example.easy_money.models.User
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.userId

        viewModel.getUser(id).collectLatest(viewLifecycleOwner) { user ->
            binding.apply {
                name.text = user.name
                address.text = user.address
                email.text = user.email
                phone.text = user.phone
                code.text = user.code
                balance.text = user.balance.toString()
                imageView.setImageResource(user.image)
                btnTransfer.setOnClickListener {
                    showDialog(user)
                }
            }
        }

    }

    private fun showDialog(user: User) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.transfer_money_dialog)
        val editText = dialog.findViewById(R.id.editText) as EditText
        val yesBtn = dialog.findViewById(R.id.btn_confirm) as Button
        val noBtn = dialog.findViewById(R.id.btn_cancel) as TextView
        yesBtn.setOnClickListener {
            val amount = editText.text.toString()
            if (amount.trim().isNotEmpty())
                confirmTransaction(user, amount.toInt(), dialog)
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.create()
        dialog.show()
    }

    private fun confirmTransaction(user: User, it: Int, dialog: Dialog) {
        if (user.balance >= it && it > 0) {
            dialog.dismiss()
            findNavController().navigate(
                DetailsFragmentDirections.actionDetailsFragmentToTransferFragment(
                    it, user.id
                )
            )
        } else Snackbar.make(requireView(), "no enough balance", Snackbar.LENGTH_SHORT).show()
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