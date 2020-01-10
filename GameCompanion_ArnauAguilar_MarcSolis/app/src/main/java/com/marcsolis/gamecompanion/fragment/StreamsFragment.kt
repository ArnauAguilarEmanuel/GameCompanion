package com.marcsolis.gamecompanion.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcsolis.gamecompanion.ListAdapters.ItemListAdapter
import com.marcsolis.gamecompanion.ListAdapters.streamsListAdapter
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.model.TWGameResponse
import com.marcsolis.gamecompanion.model.TWStreamsResponse
import com.marcsolis.gamecompanion.model.TWUserResponse
import com.marcsolis.gamecompanion.network.apiService
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_list.recyclerview
import kotlinx.android.synthetic.main.streams_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StreamsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.streams_layout, container, false)
    }

    //Init / Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    var recivedStreams: TWStreamsResponse = TWStreamsResponse()

    override fun onResume() {
        super.onResume()

        apiService.service.getGames("Call of Duty: Black Ops II").enqueue(object : Callback<TWGameResponse> {
            override fun onResponse(call: Call<TWGameResponse>, response: Response<TWGameResponse>) {
                response.body()?.data?.let { games ->
                    for (game in games) {
                        Log.i("streamsF","--------------------------------------------------------------------------------------------------------")
                        Log.i("streamsF", "Title: ${game.name} and id: ${game.id} and image: ${game.imageUrl}")

                        Picasso.get().load(game.imageUrl).into(gameImage)



                        apiService.service.getStreams(game.id.toString()).enqueue(object : Callback<TWStreamsResponse> {
                            override fun onResponse(call: Call<TWStreamsResponse>, response: Response<TWStreamsResponse>) {
                                response.body()?.data?.let { streams ->
                                    if(streams != null)recivedStreams.data = streams;
                                    var userNames :ArrayList<String> = ArrayList<String>();
                                    for(stream in streams){
                                        userNames.add(stream.user_id.toString());
                                    }

                                    apiService.service.getUsers(userNames).enqueue(object : Callback<TWUserResponse> {
                                        override fun onResponse(call: Call<TWUserResponse>, response: Response<TWUserResponse>) {
                                            response.body()?.data?.let { users ->
                                                var i =0
                                                for(user in users){
                                                    Log.i("streamsF", "user image: ${user.imageUrl}")
                                                    recivedStreams.data[i].userImageURL = user.imageUrl
                                                    i++
                                                }


                                                initUI()
                                            }
                                        }

                                        override fun onFailure(call: Call<TWUserResponse>, t: Throwable) {
                                            t.printStackTrace()
                                        }

                                    })

                                }
                            }

                            override fun onFailure(call: Call<TWStreamsResponse>, t: Throwable) {
                                t.printStackTrace()
                            }

                        })
                    }
                }
            }

            override fun onFailure(call: Call<TWGameResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })


    }

    private fun initUI() {

        // Set Layout Type
        recyclerview.layoutManager = LinearLayoutManager(this.activity)

        // Create Adapter
        val streamAdapter = streamsListAdapter()




            streamAdapter.elements = recivedStreams.data
        // RecyclerView <> Adapter
        recyclerview.adapter = streamAdapter
    }


}