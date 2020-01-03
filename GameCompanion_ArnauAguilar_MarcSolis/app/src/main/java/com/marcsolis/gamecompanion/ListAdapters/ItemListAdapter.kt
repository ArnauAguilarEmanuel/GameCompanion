package com.marcsolis.gamecompanion.ListAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.model.item
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_layout.view.*

//import kotlinx.android.synthetic.main.item_secret.view.*


class ItemListAdapter : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {

    var elements = ArrayList<item>()

    // Create View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(itemView)
    }

    // Total List Items
    override fun getItemCount(): Int {
        return elements.count()
    }

    // Recycle Visible Items
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val element = elements[position]

        // Update Views
        holder.mainWeaponName.text = element.mainWeapon
        holder.secondaryWeaponName.text = element.secondaryWeapon
        holder.className.text = element.className
        Picasso.get().load(element.mainWeaponURL).into(holder.mainWeaponImg)
        Picasso.get().load(element.secondaryWeaponURL).into(holder.secondaryWeaponImg)

        Picasso.get().load(element.secondaryWeaponAcc1URL).into(holder.secondaryAcc1Img)
        Picasso.get().load(element.secondaryWeaponAcc2URL).into(holder.secondaryAcc2Img)
        Picasso.get().load(element.mainWeaponAcc1URL).into(holder.mainAcc1Img)
        Picasso.get().load(element.mainWeaponAcc2URL).into(holder.mainAcc2Img)
        Picasso.get().load(element.mainWeaponAcc3URL).into(holder.mainAcc3Img)

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainWeaponName      = itemView.mainWeaponName
        val secondaryWeaponName = itemView.secondaryWeaponName
        val className           = itemView.className
        val mainWeaponImg       = itemView.mainWeapon
        val secondaryWeaponImg  = itemView.secondaryWeapon
        val secondaryAcc1Img    = itemView.secondaryAcc1Img
        val secondaryAcc2Img    = itemView.secondaryAcc2Img
        val mainAcc1Img         = itemView.mainAcc1Img
        val mainAcc2Img         = itemView.mainAcc2Img
        val mainAcc3Img         = itemView.mainAcc3Img
    }

}