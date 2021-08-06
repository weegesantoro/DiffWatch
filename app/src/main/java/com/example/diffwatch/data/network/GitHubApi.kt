package com.example.diffwatch.data.network


import com.example.diffwatch.data.models.commits.required.CommitsResponseItem
import com.example.diffwatch.data.models.files.required.CommitFiles
import com.example.diffwatch.data.models.pulls.required.PullsResponseItem
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

class GitHubApi {

    companion object {

        private const val BASE_URL = "https://api.github.com/"//example: BTC

        private val okHttpClientBuilder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)


        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            //.addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClientBuilder.build())
            .build()

    }


    interface APIRequests {

        @GET("repos/{user}/{repo}/pulls") // user example: square ...&&... repo example: retrofit
        suspend fun getPullsResponse(
            @Path("user") user: String,
            @Path("repo") repo: String
        ): Response<List<PullsResponseItem>>

        @GET()
        suspend fun getCommitsResponse(
            @Url url: String
        ): Response<List<CommitsResponseItem>>

        @GET()
        suspend fun getCommitDetailsResponse(
            @Url url: String
        ): Response<CommitFiles>



    }


    object API {
        val retrofitService : APIRequests by lazy {
            retrofit.create(APIRequests::class.java) }
    }

}