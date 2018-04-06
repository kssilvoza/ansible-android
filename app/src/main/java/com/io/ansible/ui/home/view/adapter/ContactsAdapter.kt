package com.io.ansible.ui.home.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.utility.ImageUtility
import com.io.ansible.data.database.entity.ContactEntity
import kotlinx.android.synthetic.main.item_contact.view.*

/**
 * Created by kimsilvozahome on 23/02/2018.
 */
class ContactsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var contactEntities: List<ContactEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(contactEntities[position])
    }

    override fun getItemCount(): Int = contactEntities.size

    fun set(contactEntities: List<ContactEntity>) {
        this.contactEntities = contactEntities
        notifyDataSetChanged()
    }

    private class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(contactEntity: ContactEntity) {
            ImageUtility.loadCircleImage(itemView.context, contactEntity.imageUrl, itemView.imageview)
            itemView.textview_name.text = contactEntity.displayName
        }
    }
}