package com.io.ansible.ui.home.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.utility.ImageUtility
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.ui.home.viewmodel.ContactsItemViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_contact.view.*

/**
 * Created by kimsilvozahome on 23/02/2018.
 */
class ContactsAdapter(private val onClickFunction: (contactEntity: ContactEntity) -> Unit) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    private var contactEntities: List<ContactEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, onClickFunction)
    }

    override fun onBindViewHolder(holder: ContactsAdapter.ViewHolder, position: Int) {
        holder.viewModel = ContactsItemViewModel(contactEntities[position])
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    override fun getItemCount(): Int = contactEntities.size

    fun set(contactEntities: List<ContactEntity>) {
        this.contactEntities = contactEntities
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View, onClickFunction: (contactEntity: ContactEntity) -> Unit): RecyclerView.ViewHolder(itemView) {
        lateinit var viewModel: ContactsItemViewModel

        val compositeDisposable = CompositeDisposable()

        init {
            itemView.layout.setOnClickListener {
                onClickFunction(viewModel.contactEntity)
            }
        }

        fun bind() {
            compositeDisposable.add(viewModel.imagePublishSubject.subscribe(this::showImage))
            compositeDisposable.add(viewModel.displayNamePublishSubject.subscribe(this::showDisplayName))
        }

        fun unbind() {
            compositeDisposable.clear()
        }

        fun showImage(imageUrl: String) {
            ImageUtility.loadCircleImage(itemView.context, imageUrl, itemView.imageview)
        }

        fun showDisplayName(displayName: String) {
            itemView.textview_name.text = displayName
        }
    }
}