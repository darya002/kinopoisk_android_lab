package com.example.androidlabkinopoisk.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.androidlabkinopoisk.R

class LoginActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences("auth", MODE_PRIVATE)

        val loginInput = findViewById<EditText>(R.id.editTextLogin)
        val passwordInput = findViewById<EditText>(R.id.editTextPassword)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        loginButton.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            val savedLogin = prefs.getString("login", null)
            val savedPassword = prefs.getString("password", null)

            if (login == savedLogin && password == savedPassword) {
                prefs.edit().putBoolean("isLoggedIn", true).apply()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val login = loginInput.text.toString()
            val password = passwordInput.text.toString()

            prefs.edit()
                .putString("login", login)
                .putString("password", password)
                .putBoolean("isLoggedIn", true)
                .apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}