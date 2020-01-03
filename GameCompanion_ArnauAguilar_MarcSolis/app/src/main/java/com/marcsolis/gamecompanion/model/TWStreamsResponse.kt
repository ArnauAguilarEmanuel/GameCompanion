package com.marcsolis.gamecompanion.model

import android.content.Context
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import com.google.gson.annotations.SerializedName
import android.view.Display
import android.content.Context.WINDOW_SERVICE
import androidx.core.content.ContextCompat.getSystemService




data class TWStream(
    var title: String?=null,
    @SerializedName("user_name")
    var username:String? = null,
    var user_id:String? = null,
    var userImageURL:String? = null,
    private var thumbnail_url: String? = null
){
    val imageUrl:String?
    get(){

        return thumbnail_url?.replace(oldValue = "{width}x{height}", newValue = "889x500");
    }
}

data class TWStreamsResponse (
    var data: ArrayList<TWStream> = ArrayList<TWStream>()
)

data class TWGame(
    var id: String?=null,
    var name:String? = null,
    private var box_art_url: String? = null
){
    val imageUrl:String?
        get(){

            return box_art_url?.replace(oldValue = "{width}x{height}", newValue = "165x213");
        }
}

data class TWGameResponse(
    var data: ArrayList<TWGame> = ArrayList<TWGame>()
)


data class TWUser(
    var view_count: Integer? = null,
    private var profile_image_url: String? = null
){
    val imageUrl:String?
        get(){

            return profile_image_url?.replace(oldValue = "{width}x{height}", newValue = "300x300");
        }
}

data class TWUserResponse(
    var data: ArrayList<TWUser> = ArrayList<TWUser>()
)