package com.io.ansible.ui.home.view.adapter

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.utility.ImageUtility
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.ui.home.viewmodel.ContactsItemViewModel
import kotlinx.android.synthetic.main.item_contact.view.*

/**
 * Created by kimsilvozahome on 23/02/2018.
 */
class ContactsAdapter(private val lifecycleOwner: LifecycleOwner, private val listener: ContactsAdapter.Listener) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private var contactEntities: List<ContactEntity> = mutableListOf()

    interface Listener {
        fun onClick(contactEntity: ContactEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, lifecycleOwner, listener)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.setViewModel(ContactsItemViewModel(contactEntities[position]))
    }

    override fun getItemCount(): Int = contactEntities.size

    fun set(contactEntities: List<ContactEntity>) {
        this.contactEntities = contactEntities
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, private val lifecycleOwner: LifecycleOwner, private val listener: Listener): RecyclerView.ViewHolder(itemView) {
        private lateinit var viewModel : ContactsItemViewModel

        private var layout = itemView.layout
        private var imageView = itemView.imageview
        private var nameTextView = itemView.textview_name

        fun setViewModel(viewModel: ContactsItemViewModel) {
            this.viewModel = viewModel

            layout.setOnClickListener {
                listener.onClick(viewModel.contactEntity)
            }

            viewModel.image.observe(lifecycleOwner, Observer { showImage(it) })
            viewModel.displayName.observe(lifecycleOwner, Observer { showDisplayName(it) })
        }

        fun showImage(imageUrl: String?) {
            if (imageUrl != null) {
                ImageUtility.loadCircleImage(itemView.context, imageUrl, imageView)
            }
        }

        fun showDisplayName(displayName: String?) {
            if (displayName != null) {
                nameTextView.text = displayName
            }
        }
    }
}