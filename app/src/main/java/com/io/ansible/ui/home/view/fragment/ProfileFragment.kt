package com.io.ansible.ui.home.view.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.io.ansible.R
import com.io.ansible.common.utility.ImageUtility
import com.io.ansible.network.ansible.model.Profile
import com.io.ansible.ui.home.viewmodel.ProfileViewModel
import dagger.android.support.AndroidSupportInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 07/02/2018.
 */
class ProfileFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel : ProfileViewModel

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        initializeViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initializeButtons(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.getProfile()
        startObserving()
    }

    override fun onStop() {
        super.onStop()
        stopObserving()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
    }

    private fun initializeButtons(view: View) {
        view.button_log_in_to_xmpp.setOnClickListener { logInToXmpp() }
    }

    private fun startObserving() {
        compositeDisposable.add(viewModel.profilePublishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(this::onProfileChange))
    }

    private fun stopObserving() {
        compositeDisposable.dispose()
    }

    private fun onProfileChange(profile: Profile) {
        ImageUtility.loadCircleImage(activity, profile.imageUrl, imageview)
        textview_name.text = profile.getName()
    }

    private fun logInToXmpp() {
        val username = edittext_username.text.toString()
        val password = edittext_password.text.toString()
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            viewModel.logInToXmpp(username, password)
        }
    }
}