package com.marcsolis.gamecompanion.network

import com.marcsolis.gamecompanion.model.TWGameResponse
import com.marcsolis.gamecompanion.model.TWStreamsResponse
import com.marcsolis.gamecompanion.model.TWUserResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface apiService {

    companion object {
        private var retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create<apiService>(apiService::class.java)
    }


    @Headers("Client-ID: ywvglt0gib8rqdly0ejobehqfi071m")
    @GET("streams")
    fun getStreams(@Query("game_id")gameId:String): Call<TWStreamsResponse>


    @Headers("Client-ID: ywvglt0gib8rqdly0ejobehqfi071m")
    @GET("games")
    fun getGames(@Query("name") gameId: String): Call<TWGameResponse>

    @Headers("Client-ID: ywvglt0gib8rqdly0ejobehqfi071m")
    @GET("users")
    fun getUsers(@Query("id") gameId: ArrayList<String>): Call<TWUserResponse>


}