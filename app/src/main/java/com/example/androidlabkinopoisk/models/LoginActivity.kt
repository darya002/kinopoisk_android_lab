package com.example.androidlabkinopoisk.models

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlabkinopoisk.MainActivity
import com.example.androidlabkinopoisk.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginField = findViewById<EditText>(R.id.editTextLogin)
        val passwordField = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            val login = loginField.text.toString()
            val password = passwordField.text.toString()

            if (login.isNotBlank() && password.isNotBlank()) {
                val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                prefs.edit()
                    .putString("login", login)
                    .putString("password", password)
                    .apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
