package com.example.sampleapplication

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.sampleapplication.databinding.ItemDataListBinding
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.FileInputStream


class MainActivtyRecyclerAdapter (clickListener: ItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //    var onItemClickListener = clickListener
    var context:Context = clickListener as Context
    interface ItemClickListener {
        fun onItemClick( position: Int)
    }

    private var onItemClickListener: ItemClickListener? = clickListener

    var dataListUI: List<ImageListEntry>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemDataListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_data_list,
            parent,
            false
        )

        return ImageListViewHolder(binding,context)
    }

    fun updateData(listDatumUIS: List<ImageListEntry>) {
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
        Log.d("ImageLoadingLibrary", "onBindViewHolder")
        when(holder) {
            is ImageListViewHolder -> {
                holder.bindTo(dataPos)
                holder.binding.dataIv.setOnClickListener{
                    onItemClickListener?.onItemClick(position = position)
                }
            }
        }
    }


}

class ImageListViewHolder(
    val binding: ItemDataListBinding, activityContext : Context
) : RecyclerView.ViewHolder(binding.root) {

    var context:Context = activityContext

    fun bindTo(dataUIPos: ImageListEntry?) {
        Log.d("ImageLoadingLibrary", "inside bind to ()")
        dataUIPos?.imageUrl?.let {
            //            binding.textData.text = it
//            Glide.with(binding.dataIv.context).load(it).centerCrop().diskCacheStrategy(
//                DiskCacheStrategy.ALL).into(binding.dataIv)
                binding.dataIv.setImageBitmap(loadImageBitmap(dataUIPos.imageName))
        }
    }


    fun loadImageBitmap(imageName: String): Bitmap? {
        var bitmap: Bitmap? = null
        val fiStream: FileInputStream
        try {
            fiStream = context.openFileInput(imageName)
            bitmap = BitmapFactory.decodeStream(fiStream)
            fiStream.close()
        } catch (e: Exception) {
            Log.d("ImageLoadingLibrary", "Exception 3, Something went wrong!")
            e.printStackTrace()
        }

        return bitmap
    }
}