package com.example.easy_money.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_money.databinding.TransactionItemBinding
import com.example.easy_money.models.Transaction

class TransactionsAdapter :
    RecyclerView.Adapter<TransactionsAdapter.TransactionVH>() {

    inner class TransactionVH(val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction) =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TransactionVH(TransactionItemBinding.inflate(LayoutInflater.from(parent.context)))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TransactionVH, position: Int) {
        holder.binding.apply {
            val transaction = differ.currentList[position]
            tvSender.text = transaction.senderName
            tvReceiver.text = transaction.receiverName
            tvAmount.text = transaction.balance.toString()
            if (transaction.completed)
                tvStatus.text = "Success"
            else
                tvStatus.text = "Canceled"
        }
    }

    override fun getItemCount() = differ.currentList.size


}