package com.marcsolis.gamecompanion.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcsolis.gamecompanion.R
import com.marcsolis.gamecompanion.activity.MainActivity
import com.marcsolis.gamecompanion.activity.RegisterActivity
import com.marcsolis.gamecompanion.model.UserModel
import com.marcsolis.gamecompanion.util.COLLECTION_USERS
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_layout.*


/**
 * A simple [Fragment] subclass.
 *
 */
class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    //Init / Main
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        initUI()
    }

    private fun initUI(){

        registerButton.setOnClickListener{
            var intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.putExtra("IsLogin", false)
            startActivity(intent)
        }
        loginButton.setOnClickListener{
            var intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.putExtra("IsLogin", true)
            startActivity(intent)
        }

        if(FirebaseAuth.getInstance().currentUser == null){
            registerButton.visibility = View.VISIBLE
            userInfo.visibility = View.GONE
            logoutButton.visibility = View.GONE
            loginButton.visibility = View.VISIBLE

        }else{
            registerButton.visibility = View.GONE
            userInfo.visibility = View.GONE
            loginButton.visibility = View.GONE

            logoutButton.visibility = View.GONE

            FirebaseFirestore.getInstance().collection(COLLECTION_USERS).document(FirebaseAuth.getInstance().currentUser?.uid ?:"").get().addOnSuccessListener {
                var myUser = it.toObject(UserModel::class.java);

                userName.text = myUser?.userName;
                prestigeName.text = myUser?.prestigeName
                prestigeLevel.text = myUser?.prestigeLevel
                wins.text = myUser?.wins.toString()
                kills.text = myUser?.kills.toString()
                playTime.text = myUser?.playTime
                score.text = myUser?.score.toString()


                var playedTimeData = Regex("[^0-9 ]").replace(myUser?.playTime.toString(), "")

                var playedTimeValue = 0
                for(i in 0..2){
                    if(playedTimeData?.indexOf(",")!=null){
                        var subStr = playedTimeData.substringBefore(' ')
                        playedTimeData = playedTimeData.removePrefix(subStr + ' ')
                        if(i == 0)
                            playedTimeValue += subStr.toInt() * 24 * 60;
                        else if(i==1)
                            playedTimeValue += subStr.toInt() * 60;
                        else
                            playedTimeValue += subStr.toInt();

                    }
                }

                if (myUser?.score != null) {
                    scorePerMin.text =  (myUser.score/playedTimeValue).toString();
                }


                if (myUser?.deaths != null) {
                    KDRatio.text = (myUser.kills/myUser.deaths).toString()
                }
                if (myUser?.losses != null) {
                    WLRatio.text = (myUser.wins/myUser.losses ).toString()

                }


                Picasso.get()
                    .load(myUser?.iconURL)
                    .into(prestigeIcon);

                registerButton.visibility = View.GONE
                userInfo.visibility = View.VISIBLE
                logoutButton.visibility = View.VISIBLE

            }
            .addOnFailureListener {
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }



            logoutButton.setOnClickListener{
                //Logout user
                FirebaseAuth.getInstance().signOut()
                //Re-build UI
                initUI()
            }
        }


    }


}
