package com.aneesh.todonotesfinal.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.aneesh.todonotesfinal.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextFullName : EditText
    private lateinit var editTextUserName : EditText
    private lateinit var buttonLogin : Button
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        onBindView()
        setClickActionToButton()

    }

    private fun setClickActionToButton() {
        buttonLogin.setOnClickListener{
            val fullName = editTextFullName.text.toString()
            val userName = editTextUserName.text.toString()

            if(fullName.isNotEmpty() && userName.isNotEmpty()){

                sharedPreferences.edit().putString("full_name", fullName).apply()
                sharedPreferences.edit().putBoolean("isLoggedIn", true).apply()

                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra("full_name", fullName)
                startActivity(intent)
            }else{
                Toast.makeText(this@LoginActivity, "FullName and UserName can't be empty!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onBindView() {
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextUserName = findViewById(R.id.editTextUserName)
        buttonLogin = findViewById(R.id.buttonLogin)
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences), MODE_PRIVATE)
    }
}