package com.io.ansible.ui.signin.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.io.ansible.R
import com.io.ansible.common.flow.FlowController
import com.io.ansible.network.facebook.FacebookRequester
import com.io.ansible.ui.signin.viewmodel.SignInViewModel
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_sign_in.*
import javax.inject.Inject

/**
 * Created by kimsilvozahome on 15/01/2018.
 */
class SignInActivity : AppCompatActivity() {
    @Inject
    lateinit var flowController: FlowController
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SignInViewModel

    private val facebookRequester: FacebookRequester = FacebookRequester(this)

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initializeViewModel()
        initializeButtons()
        startObserving()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopObserving()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookRequester.onActivityResult(requestCode, resultCode, data)
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel::class.java)
    }

    private fun initializeButtons() {
        button_sign_in_facebook.setOnClickListener(this::onFacebookButtonClicked)
        button_sign_in_twitter.setOnClickListener(this::onTwitterButtonClicked)
    }

    private fun startObserving() {
        viewModel.flow.observe(this, Observer { onFlowChange(it) })
        viewModel.spiel.observe(this, Observer { onSpielChange(it) })

        compositeDisposable.add(facebookRequester.observeToken().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onFacebookSignInSuccess))
        compositeDisposable.add(facebookRequester.observeError().observeOn(AndroidSchedulers.mainThread()).subscribe(this::onFacebookSignInError))
    }

    private fun stopObserving() {
        compositeDisposable.dispose()
    }

    private fun onFacebookButtonClicked(view: View) {
        facebookRequester.signIn()
    }

    private fun onTwitterButtonClicked(view: View) {
        // TODO - Add functionality
    }

    private fun onFacebookSignInSuccess(token: String) {
        viewModel.exchangeFacebookToken(token)
    }

    private fun onFacebookSignInError(error: String) {
        onSpielChange(SignInViewModel.SPIEL_DEFAULT_ERROR)
    }

    private fun onFlowChange(flow: Int?) {
        when (flow) {
            SignInViewModel.FLOW_TO_HOME_ACTIVITY ->
                flowController.flowToHomeActivity(this)
        }
    }

    private fun onSpielChange(spiel: Int?) {
        val resId: Int
        when (spiel) {
            SignInViewModel.SPIEL_NETWORK_ERROR ->
                resId = R.string.spiel_network_error
            else ->
                resId = R.string.spiel_default_error
        }
        Snackbar.make(layout, resId, Snackbar.LENGTH_LONG).show()
    }
}