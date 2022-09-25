package com.example.easy_money.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.easy_money.databinding.UserItemBinding
import com.example.easy_money.models.User

class UsersAdapter(private val listner: OnClickListener) :
    RecyclerView.Adapter<UsersAdapter.UserVH>() {

    inner class UserVH(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserVH(UserItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: UserVH, position: Int) {
        holder.binding.apply {
            val user = differ.currentList[position]
            imageView.setImageResource(user.image)
            tvName.text = user.name
            tvAddress.text = user.address
            tvBalance.text = user.balance.toString()
            holder.itemView.setOnClickListener {
                listner.OnClick(user)
            }
        }
    }

    interface OnClickListener {
        fun OnClick(user: User)
    }

    override fun getItemCount() = differ.currentList.size


}