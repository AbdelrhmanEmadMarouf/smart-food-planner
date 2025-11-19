package com.example.smart_food_planner.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Adapter.Chat_Bot_Adapter
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Chat_Bot_Messages
import com.example.smart_food_planner.ui.home.HomeFragment
import com.example.smart_food_planner.viewmodel.ChatViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView

class AI : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomeText: TextView
    private lateinit var messageFieldText: TextView
    private lateinit var sendButton: ImageButton
    private lateinit var backToHome: ShapeableImageView

    private val chatViewModel: ChatViewModel by viewModels()

    private val userMessageList = mutableListOf<String>()
    private val botMessageList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a_i, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewChat)
        welcomeText = view.findViewById(R.id.welcome_text)
        messageFieldText = view.findViewById(R.id.editTextMessage)
        sendButton = view.findViewById(R.id.buttonSend)
        backToHome = view.findViewById(R.id.back_to_home_arrow)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        // Handle Send button click
        sendButton.setOnClickListener {
            val message = messageFieldText.text.toString()
            if (message.isNotBlank()) {
                addMessage(message)
                chatViewModel.sendRequest(message)
                messageFieldText.text = ""

                // Hide keyboard after sending
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(messageFieldText.windowToken, 0)
            }
        }

        // Handle back to home click
        backToHome.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .replace(R.id.container, HomeFragment.newInstance())
                .commit()

            // Show bottom navigation
            val mainActivity = activity as? MainActivity
            val bottomNav = mainActivity?.findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav?.visibility = View.VISIBLE
            bottomNav?.selectedItemId = R.id.nav_home
        }

        // Observe AI responses
        chatViewModel.botReply.observe(viewLifecycleOwner) { response ->
            editLastResponseInUi(response)
        }
    }

    private fun addMessage(message: String) {
        userMessageList.add(message)
        botMessageList.add("Typing...")
        updateRecycler()
    }

    private fun editLastResponseInUi(responseMessage: String) {
        botMessageList[botMessageList.size - 1] = responseMessage
        updateRecycler()
    }

    private fun updateRecycler() {
        recyclerView.adapter = Chat_Bot_Adapter(
            requireContext(),
            Chat_Bot_Messages(userMessageList, botMessageList)
        )
        recyclerView.scrollToPosition(userMessageList.size - 1)
    }
}