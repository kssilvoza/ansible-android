package com.io.ansible.messaging

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.io.ansible.BuildConfig
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import java.lang.Exception
import java.net.InetAddress
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


/**
 * Created by kimsilvozahome on 05/03/2018.
 */
class XmppService: Service() {
    private lateinit var xmppConnection : XMPPTCPConnection

    override fun onCreate() {
        super.onCreate()
        configureConnection("192.168.44.217", 5222, "10.100.216.71")
    }

    override fun onBind(intent: Intent?): IBinder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun configureConnection(host: String, port: Int, xmppDomain: String) {
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
//                .setSocketFactory(DummySSLSocketFactory())
                .setDebuggerEnabled(BuildConfig.DEBUG)
                .build();

        xmppConnection = XMPPTCPConnection(configuration)
        xmppConnection.addConnectionListener(connectionListener)

        Thread {
            xmppConnection.connect()
            xmppConnection.login("user1", "qwertyuiop")
        }.start()
    }

    val connectionListener = object : ConnectionListener {
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

    companion object {
        private const val TAG = "XmppService"
    }
}