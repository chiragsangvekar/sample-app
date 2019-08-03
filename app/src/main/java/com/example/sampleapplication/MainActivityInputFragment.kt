package com.example.sampleapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

class MainActivityInputFragment : Fragment() {


    lateinit var binding : com.example.sampleapplication.databinding.InputFragmentBinding
    private lateinit var model: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FragmentDataBinding", "inside onCreateView : Fragment ")
        binding = DataBindingUtil.inflate(inflater,R.layout.input_fragment,container,false)

        model = activity?.run {
            ViewModelProviders.of(this)[SharedViewModel::class.java]
        } ?: throw Exception("Invalid Activity")

        binding.sharedviewmodel = model
                return binding.root
    }

}