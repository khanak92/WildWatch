package com.example.wildwatch1

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlin.collections.mapOf

class userProfile : AppCompatActivity() {
    private lateinit var imgProfilePicture: ImageView
    private lateinit var edtName: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var btnChangePassword: Button
    private lateinit var btnSaveProfile: Button
    private lateinit var btnChangeProfilePicture: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: FirebaseStorage

    private val PICK_IMAGE_REQUEST = 71
    private var imageUri: Uri? = null

    // Register the result launcher
    private val imagePickerLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                imageUri = data?.data
                imgProfilePicture.setImageURI(imageUri)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Users")
        storage = FirebaseStorage.getInstance()

        // Initialize UI elements
        imgProfilePicture = findViewById(R.id.imgProfilePicture)
        edtName = findViewById(R.id.edtName)
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber)
        btnChangePassword = findViewById(R.id.btnChangePassword)
        btnSaveProfile = findViewById(R.id.btnSaveProfile)
        btnChangeProfilePicture = findViewById(R.id.btnChangeProfilePicture)

        // Load current user info
        loadUserProfile()

        // Change profile picture
        btnChangeProfilePicture.setOnClickListener {
            chooseImage()
        }

        // Change password
        btnChangePassword.setOnClickListener {
            val user = auth.currentUser
            user?.let {
                auth.sendPasswordResetEmail(user.email!!)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Password reset email sent", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }

        // Save profile changes
        btnSaveProfile.setOnClickListener {
            saveUserProfile()
        }
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        user?.let {
            database.child(user.uid).get().addOnSuccessListener { snapshot ->
                edtName.setText(snapshot.child("name").value.toString())
                edtPhoneNumber.setText(snapshot.child("phone").value.toString())
                val profilePictureUrl = snapshot.child("profilePictureUrl").value.toString()
                if (profilePictureUrl.isNotEmpty()) {
                    // Load image using Glide or Picasso
                    Glide.with(this).load(profilePictureUrl).into(imgProfilePicture)
                }
            }
        }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    // inside saveUserProfile()
    private fun saveUserProfile() {
        val user = auth.currentUser
        user?.let {
            val name = edtName.text.toString().trim()
            val phone = edtPhoneNumber.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Name and phone cannot be empty", Toast.LENGTH_SHORT).show()
                return
            }

            val userMap = mapOf(
                "name" to name,
                "phone" to phone
            )

            // Update profile fields
            database.child(user.uid).updateChildren(userMap).addOnSuccessListener {
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
            }

            // Upload profile picture if selected
            imageUri?.let { uri ->
                val storageRef = storage.reference.child("profile_pictures/${user.uid}.jpg")
                Toast.makeText(this, "Uploading image...", Toast.LENGTH_SHORT).show()
                storageRef.putFile(uri).addOnSuccessListener {
                    storageRef.downloadUrl.addOnSuccessListener { url ->
                        database.child(user.uid).child("profilePictureUrl").setValue(url.toString())
                        Toast.makeText(this, "Image uploaded", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to upload image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
