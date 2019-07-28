package com.example.sampleapplication

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication.databinding.ItemDataListBinding
import com.example.sampleapplication.DataListAdapter.ItemClickListener





class DataListAdapter(clickListener: ItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


//    var onItemClickListener = clickListener
    interface ItemClickListener {
        fun onItemClick( position: Int)
    }

    private var onItemClickListener: ItemClickListener? = clickListener

//    fun setItemClickListener(clickListener: ItemClickListener) {
//        onItemClickListener = clickListener
//    }

//    private ItemClickListener onItemClickListener;


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
                holder.binding.dataIv.setOnClickListener{
                    onItemClickListener?.onItemClick(position = position)
                }
            }
        }
    }

}

class DataListViewHolder(
    val binding: ItemDataListBinding
) : RecyclerView.ViewHolder(binding.root) , View.OnClickListener{

    fun bindTo(dataUIPos: ListDataUIModel?) {
        Log.d("RecyclerView","Inside bindto")
        dataUIPos?.url.let {
//            binding.textData.text = it
            Glide.with(binding.dataIv.context).load(it).centerCrop().diskCacheStrategy(
                DiskCacheStrategy.ALL).into(binding.dataIv)

        }
    }

    override fun onClick(view: View?)  {
         adapterPosition
    }
}