package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.sampleapplication.databinding.ActivityMainBinding
import com.google.gson.Gson

//import kotlinx.coroutines.experimental.launch

class SearchResultsActivity : AppCompatActivity(),DataListAdapter.ItemClickListener{

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: com.example.sampleapplication.databinding.SearchResultActivityBinding
    lateinit var queryResultModel : QueryResultModel
    var listDateUIModel : MutableList<ListDataUIModel> = ArrayList<ListDataUIModel>()


    override fun onItemClick( position: Int) {
        //do something with position here
        var url = listDateUIModel[position]
        Log.d("callback","you clicked: " + url)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.search_result_activity)
        var inputJsonString = intent.getStringExtra("query_result")

        var gson = Gson()
        queryResultModel = gson.fromJson(inputJsonString,QueryResultModel::class.java)
        Log.d("New Activity", "Result :" + queryResultModel.toString())


        queryResultModel.items?.forEach{item ->

         item?.pagemap?.cseImage?.forEach{
             listDateUIModel.add(ListDataUIModel(it.src))
         }
        }
        initUI()
    }

    fun initUI() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
            binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
            binding.listRv.adapter = DataListAdapter(this)
            var adapter = binding.listRv.adapter as DataListAdapter
            adapter.updateData(listDateUIModel)
    }

}