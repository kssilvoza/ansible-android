package com.io.ansible.ui.home.view.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.viewmodel.ViewModelFactory
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.ui.home.view.adapter.ContactsAdapter
import com.io.ansible.ui.home.viewmodel.ContactsViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_contacts.view.*

/**
 * Created by kimsilvozahome on 20/02/2018.
 */
class ContactsFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var contactsAdapter: ContactsAdapter = ContactsAdapter()

    private lateinit var viewModel: ContactsViewModel

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initializeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        initializeRecyclerView(view)
        viewModel.refreshContacts()
        return view
    }

    override fun onStart() {
        super.onStart()
        startObserving()
    }

    override fun onStop() {
        super.onStop()
        stopObserving()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
    }

    private fun initializeRecyclerView(view: View) {
        view.recyclerview.layoutManager = LinearLayoutManager(this.context)
        view.recyclerview.adapter = contactsAdapter
    }

    private fun startObserving() {
        compositeDisposable.add(
                viewModel.contactsPublishSubject
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onContactsChange))
    }

    private fun stopObserving() {
        compositeDisposable.dispose()
    }

    private fun onContactsChange(contactEntities: List<ContactEntity>) {
        contactsAdapter.set(contactEntities)
    }
}