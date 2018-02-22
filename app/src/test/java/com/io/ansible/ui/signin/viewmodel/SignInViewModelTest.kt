package com.io.ansible.ui.signin.viewmodel

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
import org.mockito.MockitoAnnotations
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Created by kimsilvozahome on 09/02/2018.
 */

@RunWith(PowerMockRunner::class)
class SignInViewModelTest {
    @Rule
    @JvmField
    val rxImmediateSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    private lateinit var tokenStore: TokenStore

    private lateinit var signInViewModel: SignInViewModel

    private lateinit var successAuthTokens: AuthTokens
    private lateinit var unexpectedAnsibleError: AnsibleError

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        successAuthTokens = AuthTokens("api_token", "api_refresh_token", "messaging_token")
        unexpectedAnsibleError = AnsibleError(null, null, null, AnsibleError.Kind.UNEXPECTED)

        val authTokensSubject: PublishSubject<AuthTokens> = PublishSubject.create()
        val errorSubject: PublishSubject<AnsibleError> = PublishSubject.create()

        Mockito.`when`(tokenStore.observeAuthTokens()).thenReturn(authTokensSubject.startWith(successAuthTokens))
        Mockito.`when`(tokenStore.observeError()).thenReturn(errorSubject.startWith(unexpectedAnsibleError))

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
        val testObserver = TestObserver<Int>()

        signInViewModel.flowPublishSubject.subscribe(testObserver)
        signInViewModel.onGetTokenSuccess(successAuthTokens)

        testObserver.assertNoErrors()
        testObserver.assertValue { flow -> flow == SignInViewModel.FLOW_TO_HOME_ACTIVITY}
    }

    @Test
    fun onAnsibleError_shouldShowNetworkErrorSpiel() {
        val ansibleError = AnsibleError(null, null, null, AnsibleError.Kind.NETWORK)

        val testObserver = TestObserver<Int>()

        signInViewModel.spielPublishSubject.subscribe(testObserver)
        signInViewModel.onAnsibleError(ansibleError)

        testObserver.assertNoErrors()
        testObserver.assertValue { spiel -> spiel == SignInViewModel.SPIEL_NETWORK_ERROR}
    }

    @Test
    fun onAnsibleError_shouldShowDefaultErrorSpiel() {
        val testObserver = TestObserver<Int>()

        signInViewModel.spielPublishSubject.subscribe(testObserver)
        signInViewModel.onAnsibleError(unexpectedAnsibleError)

        testObserver.assertNoErrors()
        testObserver.assertValue { spiel -> spiel == SignInViewModel.SPIEL_DEFAULT_ERROR}
    }
}