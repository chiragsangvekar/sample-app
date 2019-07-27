package com.example.sampleapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication.databinding.ItemDataListBinding
import android.graphics.Bitmap
import android.content.Context.MODE_PRIVATE
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import java.io.FileOutputStream
import java.net.URL


class DataListAdapter(context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var dataListUI: List<ListDataUIModel>? = null
    var applicationContext = context

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
            Glide.with(binding.textData.context).load(dataUIPos?.url).centerCrop().diskCacheStrategy(
                DiskCacheStrategy.ALL).into(binding.dataIv)

        }
    }
}


