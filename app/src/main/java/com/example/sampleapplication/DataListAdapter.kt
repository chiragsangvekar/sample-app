package com.example.sampleapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication.databinding.ItemDataListBinding

class DataListAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var dataListUI: List<ListDataUIModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemDataListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_data_list,
            parent,
            false
        )

        return DataListViewHolder(binding)
    }

    fun updateData(listDatumUIS: List<ListDataUIModel>) {
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
            is DataListViewHolder -> {
                holder.bindTo(dataPos)
            }
        }
    }

}

class DataListViewHolder(
    val binding: ItemDataListBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bindTo(dataUIPos: ListDataUIModel?) {
        dataUIPos?.text.let {
            binding.textData.text = it
            Glide.with(binding.textData.context).load("https://images.unsplash.com/photo-1523676060187-f55189a71f5e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").centerCrop().diskCacheStrategy(
                DiskCacheStrategy.ALL).into(binding.dataIv)

        }
    }
}