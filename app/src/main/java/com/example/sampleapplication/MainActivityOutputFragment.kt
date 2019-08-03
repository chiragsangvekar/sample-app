package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

//import  com.example.sampleapplication.R

class MainActivityOutputFragment : Fragment() {

    lateinit var binding : com.example.sampleapplication.databinding.OutputFragmentBinding
    private lateinit var model: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentDataBinding", "inside onCreateView : Fragment ")
        binding = DataBindingUtil.inflate(inflater,R.layout.output_fragment,container,false)

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        //observe data changes
        model.user.observe(this, Observer { userList ->
            Log.d("OutputFragment","inside live data observer :"+ userList.toString())
//            binding.outputText.text = userList.toString()
//            model.userListTest = userList
            binding.listRv.layoutManager = LinearLayoutManager(activity) as RecyclerView.LayoutManager?
            binding.listRv.adapter = OutputFragmentRecyclerViewAdapter()
            var adapter = binding.listRv.adapter as OutputFragmentRecyclerViewAdapter
            userList.let {
                adapter.updateData(userList)
            }


        })

        binding.sharedviewmodel=model
        return binding.root
    }
}