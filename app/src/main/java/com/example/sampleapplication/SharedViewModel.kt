package com.example.sampleapplication

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {



      var userName : String? = null
     var userAge : String? = null

    var _user= MutableLiveData<List<UserClass>> ()
    var user:LiveData<List<UserClass>> = _user
//     var _userListTest = MutableLiveData<String> ()
//    var userListTest:LiveData<String> = _userListTest

    var userList:MutableList<UserClass>? = ArrayList<UserClass>()
//    var userName : LiveData<String> = _userName
//    var userAge : LiveData<String> = _userAge

    fun onSubmitClicked(view : View){
        Log.d("SharedViewModel","OnSubmitClick")
        if(!userName.isNullOrEmpty() && !userAge.isNullOrEmpty()){
            userList?.add(UserClass(userName,userAge))
            _user.postValue(userList)

//            _user.postValue(userList)
//            Log.d("SharedViewModel","Posted to MutableLiveData: "+ userList.toString())
        }
        return
    }

}