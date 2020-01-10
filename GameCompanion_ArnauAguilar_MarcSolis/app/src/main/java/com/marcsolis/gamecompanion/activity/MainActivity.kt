package com.marcsolis.gamecompanion.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcsolis.gamecompanion.fragment.ProfileFragment
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.fragment.ClassFragment
import com.marcsolis.gamecompanion.fragment.StreamsFragment
import com.marcsolis.gamecompanion.model.UserModel
import com.marcsolis.gamecompanion.util.COLLECTION_USERS
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_profile.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T





class MainActivity : AppCompatActivity() {
    public var myUser = UserModel()
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.getSupportActionBar()?.hide();



        bottomNavigationView.getMenu().getItem(1).setChecked(true);


        FirebaseAnalytics.getInstance(this).logEvent("User_Open_app", null)



        bottomNavigationView.setOnNavigationItemSelectedListener{menuItem ->
            when(menuItem.itemId){
                R.id.classes ->{
                    FirebaseAnalytics.getInstance(this).logEvent("Classes_Tab_Click", null)
                    //Create fragment
                    val classFragment = ClassFragment()
                    if(FirebaseAuth.getInstance().currentUser== null)
                        classFragment.user = UserModel()
                    else
                        classFragment.user = myUser
                    //Add fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, classFragment)
                    fragmentTransaction.commit()

                }
                R.id.user ->{
                    //Create fragment
                    val profileFragment = ProfileFragment()
                    //Add fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, profileFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("User_Tab_Click", null)

                }
                R.id.streams ->{

                    //Create fragment
                    val streamsFragment = StreamsFragment()
                    //Add fragment to Fragment Container
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(fragmentContainer.id, streamsFragment)
                    fragmentTransaction.commit()

                    FirebaseAnalytics.getInstance(this).logEvent("Info_Tab_Click", null)
                }
            }
            true
        }

        bottomNavigationView.selectedItemId = R.id.user;


    }

    val mediaPlayer: MediaPlayer by lazy {MediaPlayer.create(this, R.raw.bo2_main_theme)}
    override fun onResume(){
        super.onResume();

        //mediaPlayer.stop()
        mediaPlayer.start() // no need to call prepare(); create()

        if(FirebaseAuth.getInstance().currentUser != null ) {



            FirebaseFirestore.getInstance().collection(COLLECTION_USERS)
                .document(FirebaseAuth.getInstance().currentUser?.uid ?: "").get()
                .addOnSuccessListener {
                    var auxUser = it.toObject(UserModel::class.java);
                    if (auxUser != null) myUser = auxUser

                    if (myUser?.classes != null) {
                        myUser.list.add(myUser?.classes.class1)
                        myUser.list.add(myUser?.classes.class2)
                        myUser.list.add(myUser?.classes.class3)
                        myUser.list.add(myUser?.classes.class4)
                        myUser.list.add(myUser?.classes.class5)
                        myUser.list.add(myUser?.classes.class6)
                        myUser.list.add(myUser?.classes.class7)
                        myUser.list.add(myUser?.classes.class8)
                        myUser.list.add(myUser?.classes.class9)
                        myUser.list.add(myUser?.classes.class10)
                    }


                }
        }
    }
    override fun onPause() {
        super.onPause()
        mediaPlayer.pause() // no need to call prepare(); create()
    }
}
