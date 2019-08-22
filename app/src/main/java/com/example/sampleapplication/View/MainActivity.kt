package com.example.sampleapplication.View

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleapplication.Adapter.DataListAdapter
import com.example.sampleapplication.ClickListeners.ImageClickListener
import com.example.sampleapplication.Constants
import com.example.sampleapplication.Model.Items
import com.example.sampleapplication.R
import com.example.sampleapplication.ViewModel.SearchActivityViewModel
import com.example.sampleapplication.databinding.MainActivityBinding

class MainActivity : AppCompatActivity(), ImageClickListener {

    private lateinit var viewModel: SearchActivityViewModel
    private lateinit var binding : MainActivityBinding
    private lateinit var adapter: DataListAdapter
    private lateinit var dataList : List<Items>
    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.listRv.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        binding.listRv.adapter = DataListAdapter(this)
        adapter = binding.listRv.adapter as DataListAdapter
        fetchData()
    }

    override fun onResume() {
        super.onResume()
        if(isConnectedToInternet()){
            progressDialog = ProgressDialog(this)
            progressDialog.setMessage(getString(R.string.loading))
            progressDialog.setCancelable(false)
            progressDialog.show()
            viewModel.getData(intent.getStringExtra(Constants.SEARCHED_TEXT))
        } else {
            binding.heading.text = "No Internet Connection:\n Please check the connection."
        }
    }

    fun isConnectedToInternet(): Boolean {
        val connectivity = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.allNetworkInfo
            if (info != null)
                for (i in info.indices)
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }

        }
        return false
    }

    private fun fetchData() {

        viewModel = ViewModelProviders.of(this).get(SearchActivityViewModel::class.java)

        viewModel.dataListResultUIModel.observe(this, Observer {
            progressDialog.dismiss()
            it?.let {
                if(it.pages != null){
                    dataList = it.pages
                    adapter.updateData(dataList)
                } else {
                    binding.heading.text = "Response Error:\n"+it.error
                   // Toast.makeText(this, "Response erroe : "+it.error, Toast.LENGTH_LONG).show()
                }

            }
            if (it == null) {
                Toast.makeText(this, "Network issue", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onImageClickListener(item: Items?) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(Constants.DATA, item)
        startActivity(intent)
    }
}
