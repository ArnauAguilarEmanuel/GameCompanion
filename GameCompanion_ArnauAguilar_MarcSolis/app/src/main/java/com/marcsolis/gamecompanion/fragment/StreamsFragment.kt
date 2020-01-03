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
import com.marcsolis.gamecompanion.model.TWStreamsResponse
import com.marcsolis.gamecompanion.network.apiService
import kotlinx.android.synthetic.main.activity_list.*
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
        apiService.service.getStreams().enqueue(object : Callback<TWStreamsResponse> {
            override fun onResponse(call: Call<TWStreamsResponse>, response: Response<TWStreamsResponse>) {
                response.body()?.data?.let { streams ->
                    if(streams != null)recivedStreams.data = streams;
                    for (stream in streams) {
                        Log.i("streamsF","--------------------------------------------------------------------------------------------------------")
                        Log.i("streamsF", "Title: ${stream.title} and image: ${stream.imageUrl} and username: ${stream.username}")
                        Log.i("streamsF", "Stream Url: https://www.twitch.tv/${stream.username}")
                    }
                    initUI()
                }
            }

            override fun onFailure(call: Call<TWStreamsResponse>, t: Throwable) {
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