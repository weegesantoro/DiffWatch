package com.example.diffwatch.ui.pulls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diffwatch.data.models.pulls.required.PullsResponse
import com.example.diffwatch.data.models.pulls.required.PullsResponseItem
import com.example.diffwatch.data.network.GitHubApi
import kotlinx.coroutines.*

class PullRequestsViewModel : ViewModel() {

    //private val _placeHolder = MutableLiveData<String>()
    //val placeholder: LiveData<String> = _placeHolder

    var commitsUrl: String? = null

    private val _pullsResponse = MutableLiveData<List<PullsResponseItem>>()
    val pullsResponse: LiveData<List<PullsResponseItem>> = _pullsResponse



    fun sendPullsRequest(user: String, repo: String) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                withTimeout(30000){
                    val response = GitHubApi.API.retrofitService.getPullsResponse(user, repo)
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