package com.example.smart_food_planner.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.smart_food_planner.R
import com.example.smart_food_planner.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val etUserName = view.findViewById<EditText>(R.id.etUserName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = view.findViewById<EditText>(R.id.etConfirmPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val tvLogin = view.findViewById<TextView>(R.id.tvLogin)
        val guestText = view.findViewById<TextView>(R.id.registerGuestTextId)


        tvLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.add(R.id.fragmentContainer, LoginFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        guestText.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)


            requireActivity().finish()
        }

        btnRegister.setOnClickListener {
            val username = etUserName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString()
            val confirm = etConfirmPassword.text.toString()

            // basic validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(context, "Please enter a valid email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.length < 6) {
                Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirm) {
                Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // prevent double clicks
            btnRegister.isEnabled = false

            // create auth user
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { authTask ->
                    btnRegister.isEnabled = true
                    if (authTask.isSuccessful) {
                        // auth succeeded -> navigate immediately
                        val uid = auth.currentUser?.uid

                        // navigate to MainActivity right away
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                        requireActivity().finish()

                        // in background: try to save user data to Firestore
                        uid?.let { userId ->
                            val userData = hashMapOf(
                                "username" to username,
                                "email" to email
                            )
                            db.collection("users").document(userId)
                                .set(userData)
                                .addOnSuccessListener {
                                    // optional: you can show a log or silent success
                                }
                                .addOnFailureListener { e ->
                                    // log error and optionally retry later
                                    // show short toast to inform developer/user
                                    Toast.makeText(context, "Failed to save user data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        // auth failed -> show clear message
                        val ex = authTask.exception
                        val message = when (ex) {
                            is FirebaseAuthInvalidCredentialsException -> "Invalid email format"
                            is FirebaseAuthUserCollisionException -> "This email is already registered"
                            is FirebaseAuthWeakPasswordException -> "Weak password: minimum 6 characters"
                            else -> ex?.localizedMessage ?: "Register failed"
                        }
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        return view
    }





}
