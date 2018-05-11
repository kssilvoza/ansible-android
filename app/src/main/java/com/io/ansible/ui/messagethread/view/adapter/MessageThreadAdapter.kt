package com.io.ansible.ui.messagethread.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.ui.messagethread.model.MessageThreadItem

/**
 * Created by kim.silvoza on 18/04/2018.
 */
class MessageThreadAdapter() : RecyclerView.Adapter<MessageThreadAdapter.ViewHolder>() {
    private val messageThreadItems = ArrayList<MessageThreadItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(itemView)
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        return messageThreadItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val messageThreadItem = messageThreadItems.get(position)

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {

        }

        fun bind() {

        }

        fun unbind() {

        }

        fun setViewModel() {

        }
    }
}