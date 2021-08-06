package com.example.diffwatch.ui.pulls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffwatch.data.models.commits.required.CommitsResponseItem
import com.example.diffwatch.data.network.GitHubApi
import kotlinx.coroutines.*

class PullDetailsViewModel : ViewModel() {


    //private val _placeHolder = MutableLiveData<String>()
    //val placeholder: LiveData<String> = _placeHolder

    var prUrl: String? = null
    var commitsUrl: String? = null
    var commitUrl: String? = null

    private val _pullsResponse = MutableLiveData<List<CommitsResponseItem>>()
    val pullsResponse: LiveData<List<CommitsResponseItem>> = _pullsResponse


    fun sendCommitsRequest(url: String) {

        println("pullDetailsUrl = $url")

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeout(30000){
                    val response = GitHubApi.API.retrofitService.getCommitsResponse(url)
                    withContext(Dispatchers.Main){
                        _pullsResponse.value = response.body()
                        println("response.body()?.data = ${response.body()}")
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}