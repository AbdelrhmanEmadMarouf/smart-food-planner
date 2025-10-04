package com.example.chatbot.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smart_food_planner.R
import com.example.smart_food_planner.model.dataClasses.Chat_Bot_Messages


class Chat_Bot_Adapter(val context: Context, val messageList: Chat_Bot_Messages) : RecyclerView.Adapter<Chat_Bot_Adapter.ExampleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat_bot_item, parent, false)
        return ExampleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
//        val currentUserMessage = messageList.userMessages[position]
//        val currentBotIMessage = messageList.botMessages[position]
//        holder.message.text = currentUserMessage
//        holder.responseMessage.text = currentBotIMessage
//


        val currentUserMessage = messageList.userMessages[position]
        val currentBotMessage = messageList.botMessages[position]

        holder.message.text = currentUserMessage

        if (currentBotMessage == "Typing...") {
            holder.responseMessage.visibility = View.GONE
            holder.typingIndicator.visibility = View.VISIBLE

            // شغل الأنيميشن
            holder.startTypingAnimation()
        } else {
            holder.typingIndicator.visibility = View.GONE
            holder.responseMessage.visibility = View.VISIBLE
            holder.responseMessage.text = currentBotMessage
        }

    }

    override fun getItemCount(): Int {
        return messageList.userMessages.size
    }


    inner class ExampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

//        val message: TextView = view.findViewById(R.id.left_chat_text_view)
//        val responseMessage: TextView = view.findViewById(R.id.right_chat_text_view)


        val message: TextView = view.findViewById(R.id.left_chat_text_view)
        val responseMessage: TextView = view.findViewById(R.id.right_chat_text_view)
        val typingIndicator: View = view.findViewById(R.id.typing_indicator)

        fun startTypingAnimation() {
            val dot1 = typingIndicator.findViewById<View>(R.id.dot1)
            val dot2 = typingIndicator.findViewById<View>(R.id.dot2)
            val dot3 = typingIndicator.findViewById<View>(R.id.dot3)

            val anim1 = AnimationUtils.loadAnimation(itemView.context, R.anim.dot_pop).apply { startOffset = 0 }
            val anim2 = AnimationUtils.loadAnimation(itemView.context, R.anim.dot_pop).apply { startOffset = 150 }
            val anim3 = AnimationUtils.loadAnimation(itemView.context, R.anim.dot_pop).apply { startOffset = 300 }

            dot1.startAnimation(anim1)
            dot2.startAnimation(anim2)
            dot3.startAnimation(anim3)
        }

    }
}

