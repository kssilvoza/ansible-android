package com.io.ansible.ui.messagethread.view.adapter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.utility.ImageUtility
import com.io.ansible.ui.messagethread.model.MessageThreadItem
import com.io.ansible.ui.messagethread.viewmodel.MessageThreadItemViewModel
import kotlinx.android.synthetic.main.item_message.view.*

/**
 * Created by kim.silvoza on 18/04/2018.
 */
class MessageThreadAdapter(private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MessageThreadAdapter.ViewHolder>() {
    private var messageThreadItems: List<MessageThreadItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(itemView, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewModel = MessageThreadItemViewModel()
        viewModel.setMessageThreadItem(messageThreadItems[position])
        holder.setViewModel(viewModel)
    }

    override fun getItemCount(): Int {
        return messageThreadItems.size
    }

    fun set(messageThreadItems: List<MessageThreadItem>) {
        this.messageThreadItems = messageThreadItems
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(itemView) {
        private lateinit var viewModel : MessageThreadItemViewModel

        fun setViewModel(viewModel: MessageThreadItemViewModel) {
            this.viewModel = viewModel

            viewModel.isDateShown.observe(lifecycleOwner, Observer { showDate(it) })
            viewModel.isContactShown.observe(lifecycleOwner, Observer { showContact(it) })
            viewModel.date.observe(lifecycleOwner, Observer { setDate(it) })
            viewModel.contactImageUrl.observe(lifecycleOwner, Observer { setContactImageUrl(it) })
            viewModel.contactName.observe(lifecycleOwner, Observer { setContactName(it) })
            viewModel.text.observe(lifecycleOwner, Observer { setText(it) })
        }

        fun showDate(isDateShown: Boolean?) {
            if (isDateShown != null && isDateShown) {
                itemView.textview_date.visibility = View.VISIBLE
            } else {
                itemView.textview_date.visibility = View.GONE
            }
        }

        fun showContact(isContactShown: Boolean?) {
            if (isContactShown != null && isContactShown) {
                itemView.imageview_contact.visibility = View.VISIBLE
                itemView.textview_name.visibility = View.VISIBLE
            } else {
                itemView.imageview_contact.visibility = View.GONE
                itemView.textview_name.visibility = View.GONE
            }
        }

        fun setDate(date: String?) {
            itemView.textview_date.text = date
        }

        fun setContactImageUrl(imageUrl: String?) {
            ImageUtility.loadCircleImage(itemView.context, imageUrl, R.mipmap.ic_launcher_round, itemView.imageview_contact)
        }

        fun setContactName(contactName: String?) {
            itemView.textview_name.text = contactName
        }

        fun setText(text: String?) {
            itemView.textview_text.text = text
        }
    }
}