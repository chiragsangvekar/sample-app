package com.example.sampleapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
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
        }
    }
}