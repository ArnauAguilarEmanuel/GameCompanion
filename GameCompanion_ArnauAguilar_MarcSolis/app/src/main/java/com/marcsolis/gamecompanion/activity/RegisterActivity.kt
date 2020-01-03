package com.marcsolis.gamecompanion.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdRequest
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.model.UserModel
import com.marcsolis.gamecompanion.model.item
import com.marcsolis.gamecompanion.model.itemsClass
import com.marcsolis.gamecompanion.util.COLLECTION_USERS
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*
import kotlin.collections.ArrayList
import com.bumptech.glide.Glide

import android.widget.ImageView
import com.google.firebase.analytics.FirebaseAnalytics


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = getIntent()
        setContentView(R.layout.activity_register)
        this.getSupportActionBar()?.hide();


        //Login
        if(intent.getBooleanExtra("IsLogin", false)){
            usernameEditText.visibility = View.GONE
            RegisterActivityButton.visibility = View.GONE
            LoginButton.visibility = View.VISIBLE
        }
        else{
            usernameEditText.visibility = View.VISIBLE
            RegisterActivityButton.visibility = View.VISIBLE
            LoginButton.visibility = View.GONE
        }


        val adRequest = AdRequest.Builder().build()
        bannerAdView.loadAd(adRequest)

        val imageView: ImageView = loadingGif

        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/mentiras-f2250.appspot.com/o/Loading_icon%20.gif?alt=media&token=67c7060e-a28c-4094-82f8-f3a9cc272fbd").into(loadingGif)
        loadingGif.visibility = View.GONE
        InvalidCredentials.visibility = View.GONE;



        //region RegisterButton
        RegisterActivityButton.setOnClickListener {
            loadingGif.visibility = View.VISIBLE

            val username = usernameEditText.text?.toString().orEmpty()
            val email = emalEditText.text?.toString().orEmpty()
            val password = paswordEditText.text?.toString().orEmpty()

            if(username.trim().isEmpty()){
                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = getString(R.string.error_register_invalid_username);
                loadingGif.visibility = View.GONE



                return@setOnClickListener
            }

            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = getString(R.string.error_register_invalid_email);
                loadingGif.visibility = View.GONE


                return@setOnClickListener
            }

            if(password.isBlank() || !isPasswordValid(password)){
                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = getString(R.string.error_register_invalid_password)
                loadingGif.visibility = View.GONE

                return@setOnClickListener
            }

            var weapons : ArrayList< Pair<String, String>>
            weapons = ArrayList()

            var accesories : ArrayList<String>
            accesories = ArrayList()

            var prestiges : ArrayList<Pair<String, String>>
            prestiges = ArrayList()

            FirebaseFirestore.getInstance().collection("accesories")
                .get()
                .addOnSuccessListener { result ->
                        for(doc in result.documents)
                            accesories.add(doc["imageUrl"].toString())

                    FirebaseFirestore.getInstance().collection("weapons")
                        .get()
                        .addOnSuccessListener { result ->
                            for(doc in result.documents)
                                weapons.add(Pair(doc["weaponName"].toString(),doc["imageUrl"].toString()))

                            FirebaseFirestore.getInstance().collection("prestiges")
                                .get()
                                .addOnSuccessListener { result ->
                                    for(doc in result.documents)
                                        prestiges.add(Pair(doc.id,doc["imageUrl"].toString()))

                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->

                                        var classes : itemsClass
                                        classes = itemsClass()
                                        var items : ArrayList<item>
                                        items = ArrayList<item>()

                                        var newPrestige : Pair<String, String>
                                        newPrestige = prestiges.shuffled()[0]

                                        for(i in 0..9){
                                            var weapon = weapons.shuffled()[0]
                                            var weapon2 = weapons.shuffled()[0]
                                            items.add(item(weapon.first,weapon.second,weapon2.first,weapon2.second,accesories.shuffled()[0],accesories.shuffled()[0],accesories.shuffled()[0],accesories.shuffled()[0],accesories.shuffled()[0],"Class "+ i))
                                        }

                                        classes.class1 = items[0]
                                        classes.class2 = items[1]
                                        classes.class3 = items[2]
                                        classes.class4 = items[3]
                                        classes.class5 = items[4]
                                        classes.class6 = items[5]
                                        classes.class7 = items[6]
                                        classes.class8 = items[7]
                                        classes.class9 = items[8]
                                        classes.class10 = items[9]

                                        //Create user profile
                                        val userModel = UserModel(
                                            userId = authResult.user?.uid ?: "",
                                            userName = username,
                                            userEmail = email,

                                            iconURL = newPrestige.second,
                                            prestigeName = newPrestige.first,
                                            prestigeLevel = (1 + Random().nextInt(54)).toString(),
                                            kills = Random().nextInt(10000),
                                            deaths = Random().nextInt(10000),
                                            losses = Random().nextInt(10000),
                                            wins = Random().nextInt(10000),
                                            playTime = Random().nextInt(10).toString()+"d, "+Random().nextInt(23).toString()+"h, "+Random().nextInt(60).toString()+"m, "+Random().nextInt(60).toString()+"s",
                                            score= Random().nextInt(100000),
                                            classes = classes
                                        )

                                        FirebaseAnalytics.getInstance(this).logEvent("User_Register", null)



                                        FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(authResult.user?.uid ?:"").set(userModel).addOnSuccessListener {
                                            finish()
                                        }
                                            .addOnFailureListener {
                                                FirebaseAnalytics.getInstance(this).logEvent("Error_UpdateUserToFirestore", null)
                                                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
                                            }


                                    }.addOnFailureListener {
                                        Toast.makeText(this, "Error while register: " + it.localizedMessage, Toast.LENGTH_LONG).show()
                                        loadingGif.visibility = View.GONE;
                                        InvalidCredentials.visibility = View.VISIBLE;
                                        InvalidCredentials.text = it.localizedMessage;
                                        FirebaseAnalytics.getInstance(this).logEvent("Error_Register", null)

                                    }.addOnCanceledListener {
                                        Toast.makeText(this, "Register canceled", Toast.LENGTH_LONG).show()

                                    }

                                }
                                .addOnFailureListener { exception ->
                                }
                        }
                        .addOnFailureListener { exception ->
                        }
                }
                .addOnFailureListener { exception ->
            }
        }
        //endregion

        //region Login
        LoginButton.setOnClickListener {
            loadingGif.visibility = View.VISIBLE

            val email = emalEditText.text?.toString().orEmpty()
            val password = paswordEditText.text?.toString().orEmpty()

            if(email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = getString(R.string.error_register_invalid_email);
                loadingGif.visibility = View.GONE
                return@setOnClickListener
            }

            if(password.isBlank() || !isPasswordValid(password)){

                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = getString(R.string.error_register_invalid_password);
                loadingGif.visibility = View.GONE

                return@setOnClickListener
            }


            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener { authResult ->
                FirebaseAnalytics.getInstance(this).logEvent("Login", null)

                finish()

            }.addOnFailureListener {
                InvalidCredentials.visibility = View.VISIBLE;
                InvalidCredentials.text = it.localizedMessage;
                loadingGif.visibility = View.GONE

                FirebaseAnalytics.getInstance(this).logEvent("Error_Login", null)

            }.addOnCanceledListener {
                Toast.makeText(this, "Log in cancelled", Toast.LENGTH_LONG).show()
            }


        }
        //endregion

    }




    private fun isPasswordValid(password: String): Boolean{
        var number = false
        var letter = false
        if(password.length < 4) return false
        password.forEach {
            if(it.isDigit()) number = true
            if(it.isLetter()) letter = true
        }

        return number && letter
    }

}
