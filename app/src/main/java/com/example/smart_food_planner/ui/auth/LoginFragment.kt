package com.example.smart_food_planner.ui.auth

import android.content.Intent
import android.os.Bundle
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
import com.example.smart_food_planner.ui.home
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var guestText: TextView
    private lateinit var createAccountText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Find views
        emailEditText = view.findViewById(R.id.email_edittext)
        passwordEditText = view.findViewById(R.id.password_edittext)
        loginButton = view.findViewById(R.id.login_button)
        guestText = view.findViewById(R.id.guest_text)
        createAccountText = view.findViewById(R.id.create_account_text)

        // Login with Email/Password
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, password)
            }
        }



        // Continue as Guest
        guestText.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)


            requireActivity().finish()
        }

        // Navigate to Register page
        createAccountText.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, RegisterFragment())
                ?.addToBackStack(null)
                ?.commit()
        }

        return view
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {


                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)


                    requireActivity().finish()



                } else {
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}