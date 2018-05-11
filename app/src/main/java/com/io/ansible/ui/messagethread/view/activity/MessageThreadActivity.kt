package com.io.ansible.ui.messagethread.view.activity

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.io.ansible.R
import com.io.ansible.ui.messagethread.model.MessageThreadItem
import com.io.ansible.ui.messagethread.viewmodel.MessageThreadViewModel
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_message_thread.*
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 11/04/2018.
 */
class MessageThreadActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: MessageThreadViewModel

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_thread)
        initializeViewModel()
        initializeButtons()
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageThreadViewModel::class.java)
        viewModel.setId(intent.getStringExtra(INTENT_EXTRA_ID))
    }

    private fun initializeButtons() {
        button_send.setOnClickListener { onSendButtonClicked() }
    }

    private fun startObserving() {
        compositeDisposable.add(viewModel.editTextPublishSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onEditTextChanged))
        compositeDisposable.add(viewModel.messageThreadItemsPublishSubject
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMessageThreadItemsChanged))
    }

    private fun stopObserving() {
        compositeDisposable.clear()
    }

    private fun onSendButtonClicked() {
        viewModel.sendMessage(edittext.text.toString())
    }

    private fun onEditTextChanged(text: String) {
        edittext.setText(text)
    }

    private fun onMessageThreadItemsChanged(messageThreadItems: List<MessageThreadItem>) {

    }

    companion object {
        const val INTENT_EXTRA_ID = "id"
    }
}