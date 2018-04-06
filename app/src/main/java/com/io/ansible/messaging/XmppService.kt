package com.io.ansible.messaging

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.io.ansible.BuildConfig
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.Stanza
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate
import java.lang.Exception
import java.net.InetAddress
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

/**
 * Created by kimsilvozahome on 05/03/2018.
 */
class XmppService: Service() {
    private lateinit var xmppConnection : XMPPTCPConnection
    private lateinit var chatManager: ChatManager

    private lateinit var disposable: Disposable

    override fun onCreate() {
        super.onCreate()
        // Host may change. Check network settings to see host
        initializeConnection(HOST, PORT, XMPP_DOMAIN)
        startObserving()
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        super.onDestroy()
        stopObserving()
    }

    private fun initializeConnection(host: String, port: Int, xmppDomain: String) {
        Log.d(TAG, "Configure Connection")
        val address = InetAddress.getByName(host)
        val verifier = object : HostnameVerifier {
            override fun verify(hostname: String, session: SSLSession): Boolean {
                return false
            }
        }
        val configuration = XMPPTCPConnectionConfiguration.builder()
                .setHost(host)
                .setHostAddress(address)
                .setHostnameVerifier(verifier)
                .setPort(port)
                .setXmppDomain(xmppDomain)
                .setSendPresence(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                .setDebuggerEnabled(BuildConfig.DEBUG)
                .build();

        xmppConnection = XMPPTCPConnection(configuration)
        xmppConnection.addConnectionListener(connectionListener)
        xmppConnection.addSyncStanzaListener(this::processStanza, this::acceptStanza)

        chatManager = ChatManager.getInstanceFor(xmppConnection)
        chatManager.addIncomingListener(this::onIncomingChatMessage)

        Thread {
            xmppConnection.connect()
        }.start()
    }

    private fun startObserving() {
        disposable = MessageBus.requestPublishSubject
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onRequestReceived)
    }

    private fun stopObserving() {
        disposable.dispose()
    }

    private val connectionListener = object : ConnectionListener {
        override fun connected(connection: XMPPConnection?) {
            Log.d(TAG, "Connected")
        }

        override fun connectionClosed() {
            Log.d(TAG, "Connection Closed")
        }

        override fun connectionClosedOnError(e: Exception?) {
        }

        override fun reconnectionSuccessful() {
        }

        override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
            Log.d(TAG, "Authenticated")
        }

        override fun reconnectionFailed(e: Exception?) {
        }

        override fun reconnectingIn(seconds: Int) {
        }
    }

    private fun processStanza(stanza: Stanza) {
        Log.d(TAG, "Stanza: ${stanza.toXML()}")
    }

    private fun acceptStanza(stanza: Stanza): Boolean {
        return true
    }

    private fun onRequestReceived(messageRequest: MessageRequest) {
        val payload = messageRequest.payload
        when(messageRequest.type) {
            MessageRequest.TYPE_LOG_IN -> {
                if (payload is LogInPayload) {
                    logIn(payload.username, payload.password)
                }
            }
            MessageRequest.TYPE_SEND_CHAT_MESSAGE -> {
            }
        }
    }

    private fun logIn(username: String, password: String) {
        Thread {
            xmppConnection.login(username, password)
        }.start()
    }

    private fun sendChatMessage(username: String, messageBody: String) {
        Thread {
            val jid = JidCreate.entityBareFrom("$username@$XMPP_DOMAIN")
            val chat = chatManager.chatWith(jid)
            val message = Message(jid, Message.Type.chat)
            message.body = messageBody
            chat.send(message)
        }.start()
    }

    private fun onIncomingChatMessage(from: EntityBareJid, message: Message, chat: Chat) {
        Log.d(TAG, "*****Incoming Chat*****")
        Log.d(TAG, "From: $from")
        Log.d(TAG, "Message: ${message.body}")
        Log.d(TAG, "Message From: ${message.from}")
        Log.d(TAG, "Chat: $chat")
    }

    companion object {
        private const val TAG = "XmppService"

        private const val HOST = "10.100.216.125"
        private const val PORT = 5222
        private const val XMPP_DOMAIN = "10.100.216.71"
    }
}