package com.example.smart_food_planner.ui

import android.R.attr.fragment
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.Adapter.Chat_Bot_Adapter
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Chat_Bot_Messages
import com.example.smart_food_planner.viewmodel.ChatViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView


class AI : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var welcomText: TextView
    private lateinit var messageFildText: TextView
    private lateinit var SendButton: ImageButton

    private lateinit var backToHome : ShapeableImageView

    private val chatViewModel: ChatViewModel by viewModels()

    val userMessageList = mutableListOf<String>()
    val botMessageList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_i, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        recyclerView = view.findViewById(R.id.recyclerViewChat)
        welcomText = view.findViewById(R.id.welcome_text)
        messageFildText = view.findViewById(R.id.editTextMessage)
        SendButton = view.findViewById(R.id.buttonSend)
        backToHome = view.findViewById(R.id.back_to_home_arrow)

        SendButton.setOnClickListener {
            if (!messageFildText.text.isBlank()) {
                addMessage(messageFildText.text.toString())
                chatViewModel.sendRequest(messageFildText.text.toString())
                messageFildText.text = ""

                // إغلاق الكيبورد في Fragment
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(messageFildText.windowToken, 0)

            }
        }

        backToHome.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, home())
                .commit()

            val mainActivity = activity as MainActivity
            val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNav.visibility = View.VISIBLE


            bottomNav.selectedItemId = R.id.nav_home

        }

        chatViewModel.botReply.observe(viewLifecycleOwner){
            editLastResponseInUi(it)
        }


        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)


        super.onViewCreated(view, savedInstanceState)
    }



    private fun addMessage(mess: String) {
        userMessageList.add(mess)
        botMessageList.add("Typing...")
        getRecyclerData()
    }

    private fun getRecyclerData() {
        recyclerView.adapter =
            Chat_Bot_Adapter(requireContext(), Chat_Bot_Messages(userMessageList, botMessageList))
        recyclerView.scrollToPosition(userMessageList.size - 1)
    }

    private fun editLastResponseInUi(responseMessage: String) {
        botMessageList[botMessageList.size - 1] = responseMessage
        getRecyclerData()
    }


}

