package com.io.ansible.ui.signin.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.io.ansible.data.store.TokenStore
import com.io.ansible.network.ansible.model.AnsibleError
import com.io.ansible.network.ansible.model.AuthTokens
import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import mockito.RxImmediateSchedulerRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Created by kimsilvozahome on 09/02/2018.
 */

@RunWith(PowerMockRunner::class)
class SignInViewModelTest {
    @Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var tokenStore: TokenStore
    @Mock
    private lateinit var intObserver: Observer<Int>

    private lateinit var signInViewModel: SignInViewModel

    private lateinit var successAuthTokens: AuthTokens
    private lateinit var unexpectedAnsibleError: AnsibleError

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        successAuthTokens = AuthTokens("api_token", "api_refresh_token", "messaging_token")
        unexpectedAnsibleError = AnsibleError(null, null, null, AnsibleError.Kind.UNEXPECTED)

        val authTokensSubject: PublishSubject<AuthTokens> = PublishSubject.create()

        Mockito.`when`(tokenStore.observeAuthTokens()).thenReturn(authTokensSubject.startWith(successAuthTokens))

        signInViewModel = SignInViewModel(tokenStore)
    }

    @Test
    fun exchangeFacebookToken_shouldExchangeUsingStore() {
        val facebookToken = "facebook_token"
        signInViewModel.exchangeFacebookToken(facebookToken)

        Mockito.verify(tokenStore).exchangeFacebookToken(facebookToken)
    }

    @Test
    fun onGetTokenSuccess_shouldFlowToHome() {
        signInViewModel.flow.observeForever(intObserver)
        signInViewModel.onGetTokenSuccess(successAuthTokens)

        assert(signInViewModel.flow.value == SignInViewModel.FLOW_TO_HOME_ACTIVITY)
    }

    @Test
    fun onAnsibleError_shouldShowNetworkErrorSpiel() {
        val ansibleError = AnsibleError(null, null, null, AnsibleError.Kind.NETWORK)

        signInViewModel.spiel.observeForever(intObserver)
        signInViewModel.onAnsibleError(ansibleError)

        assert(signInViewModel.spiel.value == SignInViewModel.SPIEL_NETWORK_ERROR)
    }

    @Test
    fun onAnsibleError_shouldShowDefaultErrorSpiel() {
        signInViewModel.spiel.observeForever(intObserver)
        signInViewModel.onAnsibleError(unexpectedAnsibleError)

        assert(signInViewModel.spiel.value == SignInViewModel.SPIEL_DEFAULT_ERROR)
    }
}