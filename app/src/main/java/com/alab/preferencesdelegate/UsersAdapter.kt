package com.alab.preferencesdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alab.preferencesdelegate.databinding.UserHolderBinding

/**
 * User list adapter.
 * @param viewLifecycleOwner View lifecycle owner.
 * @param onItemClick Handler for clicking on an item.
 * @param onItemRemove Item remove Handler.
 */
class UsersAdapter(
    private val viewLifecycleOwner: LifecycleOwner,
    private val onItemClick: (item: User) -> Unit,
    private val onItemRemove: (item: User) -> Unit
) : ListAdapter<User, UsersAdapter.ViewHolder>(
    DiffUtilCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            UserHolderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ).also { vh ->
            vh.binding.apply {
                item.setOnClickListener {
                    onItemClick(currentList[vh.adapterPosition])
                }
                userRemoveImage.setOnClickListener {
                    onItemRemove(currentList[vh.adapterPosition])
                }
            }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ViewHolder(val binding: UserHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User) {
            binding.apply {
                lifecycleOwner = viewLifecycleOwner
                user = item
                executePendingBindings()
            }
        }
    }

    private class DiffUtilCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User) = oldItem == newItem
        override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
    }
}