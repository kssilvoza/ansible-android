package com.io.ansible.ui.home.view.fragment

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)
        viewModel.getProfile()
    }

    private fun startObserving() {
        compositeDisposable.add(viewModel.mProfilePublishSubject.observeOn(AndroidSchedulers.mainThread()).subscribe(this::onProfileChange))
    }

    private fun stopObserving() {
        compositeDisposable.dispose()
    }

    private fun onProfileChange(profile: Profile) {
        ImageUtility.loadCircleImage(activity, profile.imageUrl, imageview)
        textview_name.text = profile.getName()
    }
}