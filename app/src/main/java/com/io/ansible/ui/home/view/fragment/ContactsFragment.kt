package com.io.ansible.ui.home.view.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.flow.FlowController
import com.io.ansible.data.database.entity.ContactEntity
import com.io.ansible.ui.home.view.adapter.ContactsAdapter
import com.io.ansible.ui.home.viewmodel.ContactsViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_contacts.view.*
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 20/02/2018.
 */
class ContactsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    @Inject
    lateinit var flowController: FlowController

    private lateinit var contactsAdapter: ContactsAdapter

    private lateinit var viewModel: ContactsViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initializeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_contacts, container, false)
        initializeRecyclerView(view)
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
        viewModel.refreshContacts()
    }

    private fun initializeRecyclerView(view: View) {
        contactsAdapter = ContactsAdapter(this, object : ContactsAdapter.Listener {
            override fun onClick(contactEntity: ContactEntity) {
                onContactClicked(contactEntity)
            }
        })
        view.recyclerview.layoutManager = LinearLayoutManager(this.context)
        view.recyclerview.adapter = contactsAdapter
    }

    private fun startObserving() {
        compositeDisposable.add(viewModel.contactsPublishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(this::onContactsChange))
        compositeDisposable.add(viewModel.flowPublishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(this::onFlowChange))
    }

    private fun stopObserving() {
        compositeDisposable.clear()
    }

    private fun onContactsChange(contactEntities: List<ContactEntity>) {
        contactsAdapter.set(contactEntities)
    }

    private fun onContactClicked(contactEntity: ContactEntity) {
        Log.d("ContactsFragment", "Contact Name: ${contactEntity.displayName}\nImage Url: ${contactEntity.imageUrl}")
        viewModel.onContactClicked(contactEntity)
    }

    private fun onFlowChange(pair: Pair<Int, Any>) {
        when (pair.first) {
            ContactsViewModel.FLOW_TO_MESSAGE_THREAD_ACTIVITY ->
                if (pair.second is ContactEntity) {
                    val contactEntity = pair.second as ContactEntity
                    flowController.flowToMessageThreadActivity(activity, contactEntity.id)
                }
        }
    }
}