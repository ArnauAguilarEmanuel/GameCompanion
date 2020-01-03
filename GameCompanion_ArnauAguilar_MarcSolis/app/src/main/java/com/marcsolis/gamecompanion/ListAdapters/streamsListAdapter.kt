package com.marcsolis.gamecompanion.ListAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.model.TWStream
import com.marcsolis.gamecompanion.model.item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.stream_layout.view.*
import com.marcsolis.gamecompanion.model.CircleTransform






class streamsListAdapter: RecyclerView.Adapter<streamsListAdapter.ViewHolder>() {
    var elements:ArrayList<TWStream> = ArrayList<TWStream>()

    // Create View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(com.marcsolis.gamecompanion.R.layout.stream_layout, parent, false)
        return ViewHolder(itemView)
    }

    // Total List Items
    override fun getItemCount(): Int {
       return elements.count()

    }

    // Recycle Visible Items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = elements?.get(position)

        // Update Views
        holder.username.text = element.username

        holder.title.text = element.title

        Picasso.get().load(element.imageUrl).into(holder.thumbnail)
        //Picasso.get().load(element.userImageURL).into(holder.userImage)
        Picasso.get().load(element.userImageURL).transform(CircleTransform()).into(holder.userImage)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username = itemView.username
        val title= itemView.title
        val thumbnail = itemView.thumbnail
        val userImage = itemView.userImage
    }

}