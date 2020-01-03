package com.marcsolis.gamecompanion.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.marcsolis.gamecompanion.ListAdapters.ItemListAdapter
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.model.UserModel
import kotlinx.android.synthetic.main.activity_list.*



class ClassFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_list, container, false)
    }


    //Init / Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        if(user == UserModel())
            recyclerview.visibility = View.GONE
        else{
            recyclerview.visibility = View.VISIBLE
            initUI()

        }
    }

    public var user = UserModel()

    private fun initUI() {

        // Set Layout Type
        recyclerview.layoutManager = LinearLayoutManager(this.activity)

        // Create Adapter
        val itemAdapter = ItemListAdapter()





        itemAdapter.elements = user.list
        // RecyclerView <> Adapter
        recyclerview.adapter = itemAdapter
    }


}
