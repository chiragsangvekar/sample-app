package com.example.sampleapplication

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.databinding.ItemDataListBinding

class MainActivityRecyclerViewFragment(imageList : List<ImageListEntry>,activityContext : MainActivtyRecyclerAdapter.ItemClickListener) : Fragment() ,DataListAdapter.ItemClickListener{
    override fun onItemClick(position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var binding : com.example.sampleapplication.databinding.MainActivityRecyclerViewFragmentBinding
    lateinit var adapter : MainActivtyRecyclerAdapter
    var activityContext  = activityContext
    var imageList = imageList

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("ImageLoadingLibrary", "inside onCreateView : Fragment ; data received"+imageList.toString() )
        binding = DataBindingUtil.inflate(inflater,R.layout.main_activity_recycler_view_fragment,container,false)

        setAdapter()
        adapter.updateData(imageList)

        return binding.root
    }

    fun setAdapter() {
        Log.d("ImageLoadingLibrary", "Initializing recyclerView")
        binding.listRv.layoutManager = LinearLayoutManager(context)
        binding.listRv.adapter = MainActivtyRecyclerAdapter(activityContext)
        adapter = binding.listRv.adapter as MainActivtyRecyclerAdapter
    }
}