package com.aneesh.todonotesfinal.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.login.LoginActivity
import com.aneesh.todonotesfinal.myNotes.MyNotesActivity
import com.aneesh.todonotesfinal.onboarding.OnBoardingActivity
import com.aneesh.todonotesfinal.data.local.pref.PrefConstant
import com.aneesh.todonotesfinal.data.local.pref.StoreSession
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class SplashActivity : AppCompatActivity() {


    //Handler executes a task on a different thread,
    //So, it's mandatory for us to remove the task from thread on backpress, else it will still execute itself
    lateinit var handler: Handler
    lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpSharedPreferences()
        gotoNext()
    }

    private fun gotoNext() {
        handler = Handler()
        runnable = Runnable {
            checkOnBoardedStatus()
            getFCMToken()
            finish()
        }
        handler.postDelayed(runnable, 2000)
    }

    private fun checkOnBoardedStatus() {
        val onBoarded = StoreSession.readBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY)
        if(onBoarded!!){
            checkLoginStatus()
        }else{
            val intent = Intent(this@SplashActivity, OnBoardingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("SplashActivity", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("SplashActivity", "token $token")
            //Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }

    private fun checkLoginStatus() {
        val isLoggedIn = StoreSession.readBoolean(PrefConstant.IS_LOGGED_IN)
        Log.d("SplashActivity", isLoggedIn.toString())
        if (isLoggedIn!!) {
            val intent = Intent(this@SplashActivity, MyNotesActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        handler.removeCallbacks(runnable)
    }
}