package com.aneesh.todonotesfinal.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aneesh.todonotesfinal.R
import com.aneesh.todonotesfinal.myNotes.MyNotesActivity
import com.aneesh.todonotesfinal.data.local.pref.PrefConstant
import com.aneesh.todonotesfinal.data.local.pref.StoreSession

class LoginActivity : AppCompatActivity() {

    private lateinit var textInputFullName : EditText
    private lateinit var textInputUserName : EditText
    private lateinit var buttonLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setUpSharedPreferences()
        onBindView()
        setClickActionToButton()

    }

    private fun setUpSharedPreferences() {
        StoreSession.init(this)
    }

    private fun setClickActionToButton() {
        buttonLogin.setOnClickListener{
            val fullName = textInputFullName.text.toString()
            val userName = textInputUserName.text.toString()

            if(fullName.isNotEmpty() && userName.isNotEmpty()){

                StoreSession.write(PrefConstant.FULL_NAME, fullName)
                Log.d("LoginActivity", StoreSession.readString(PrefConstant.FULL_NAME).toString())
                StoreSession.write(PrefConstant.IS_LOGGED_IN , true)

                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(PrefConstant.FULL_NAME, fullName)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this@LoginActivity, "FullName and UserName can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onBindView() {
        textInputFullName = findViewById(R.id.textInputFullName)
        textInputUserName = findViewById(R.id.textInputUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
    }
}