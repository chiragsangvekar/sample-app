package com.example.sampleapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication.databinding.ItemDataListBinding
import com.example.sampleapplication.databinding.RecyclerViewItemBinding

class OutputFragmentRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataListUI: List<UserClass>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<com.example.sampleapplication.databinding.RecyclerViewItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_item,
                parent,
                false
        )

        return UserListViewHolder(binding)
    }

    fun updateData(listDatumUIS: List<UserClass>) {
        dataListUI = listDatumUIS
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if(dataListUI == null) {
            return 0
        } else {
            return dataListUI!!.size
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataPos = dataListUI?.get(position)

        when(holder) {
            is UserListViewHolder -> {
                holder.bindTo(dataPos)
            }
        }
    }

}

class UserListViewHolder(
        val binding: RecyclerViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(dataUIPos: UserClass?) {
        dataUIPos?.userName.let {
            binding.userName.setText(it)

        }
        dataUIPos?.userAge.let {
            binding.userAge.setText(it)
        }
    }
}