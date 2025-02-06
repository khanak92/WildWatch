package com.example.wildwatch1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class changePassword : AppCompatActivity() {
    private lateinit var edtOldPassword: EditText
    private lateinit var edtNewPassword: EditText
    private lateinit var edtConfirmNewPassword: EditText
    private lateinit var btnChangePassword: Button
    private lateinit var btnForgotPassword: Button

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_change_password)
        auth = FirebaseAuth.getInstance()

        // Initialize UI Elements
        edtOldPassword = findViewById(R.id.edtOldPassword)
        edtNewPassword = findViewById(R.id.edtNewPassword)
        edtConfirmNewPassword = findViewById(R.id.edtConfirmNewPassword)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnForgotPassword = findViewById(R.id.btnForgotPassword)

        // Handle Password Change
        btnChangePassword.setOnClickListener {
            changeUserPassword()
        }

        // Handle Forgot Password
        btnForgotPassword.setOnClickListener {
            sendPasswordResetEmail()
        }
    }

    private fun changeUserPassword() {
        val user = auth.currentUser
        val oldPassword = edtOldPassword.text.toString().trim()
        val newPassword = edtNewPassword.text.toString().trim()
        val confirmNewPassword = edtConfirmNewPassword.text.toString().trim()

        if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmNewPassword) {
            Toast.makeText(this, "New passwords do not match!", Toast.LENGTH_SHORT).show()
            return
        }

        user?.let {
            val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

            // Reauthenticate User
            user.reauthenticate(credential).addOnCompleteListener { authTask ->
                if (authTask.isSuccessful) {

                    // Update Password
                    user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(this, "Password Changed Successfully!", Toast.LENGTH_SHORT).show()

                            // Return to User Profile Screen
                            val intent = Intent(this, userProfile::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Error: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(this, "Authentication Failed: Wrong Old Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Forgot Password - Sends Reset Email
    private fun sendPasswordResetEmail() {
        val user = auth.currentUser

        if (user?.email.isNullOrEmpty()) {
            Toast.makeText(this, "Error: No email associated with account", Toast.LENGTH_SHORT).show()
            return
        }

        auth.sendPasswordResetEmail(user?.email!!)
            .addOnSuccessListener {
                Toast.makeText(this, "Reset email has been sent! Check your inbox.", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }
}