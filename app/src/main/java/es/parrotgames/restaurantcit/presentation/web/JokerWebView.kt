package es.parrotgames.restaurantcit.presentation.web

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.ui.viewinterop.AndroidView
import dagger.hilt.android.AndroidEntryPoint
import es.parrotgames.restaurantcit.presentation.lepry_game.LepryGame
import es.parrotgames.restaurantcit.presentation.viewmodel.JokerViewModel


@AndroidEntryPoint
class JokerWebView : ComponentActivity() {

    private val viewModel: JokerViewModel by viewModels()
    private lateinit var callback: ValueCallback<Array<Uri?>>

    val launcher =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            callback.onReceiveValue(it.toTypedArray())
        }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var lepryBrewWebView: WebView? = null

            AndroidView(factory = {
                val ua = WebView(this@JokerWebView).settings.userAgentString.replace(" wv", "")

                WebView(this@JokerWebView).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    loadUrl(requireNotNull(intent.getStringExtra("url")))
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.loadWithOverviewMode = false
                    settings.userAgentString = ua

                    CookieManager.getInstance().setAcceptCookie(true)
                    CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

                    onBackPressedDispatcher.addCallback(this@JokerWebView,
                        object : OnBackPressedCallback(true) {
                            override fun handleOnBackPressed() {
                                if (lepryBrewWebView?.canGoBack() == true) {
                                    lepryBrewWebView?.goBack()
                                }
                            }
                        })

                    webViewClient = object : WebViewClient() {
                        override fun onReceivedError(
                            view: WebView,
                            request: WebResourceRequest,
                            error: WebResourceError
                        ) {
                            super.onReceivedError(view, request, error)
                        }

                        override fun onPageFinished(view: WebView, url: String) {
                            super.onPageFinished(view, url)
                            CookieManager.getInstance().flush()

                            if (url == "https://luckywinner.store/") {
                                startLepryGame()
                            } else if (url.contains("404")) {
                                Log.i("ON_PAGE_FINISHED", "current url with 404: $url")
                            } else if (!url.contains("luckywinner.store")) {
                                viewModel.saveUrl(url)
                            }
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onShowFileChooser(
                            webView: WebView,
                            filePathCallback: ValueCallback<Array<Uri?>>,
                            fileChooserParams: FileChooserParams
                        ): Boolean {
                            callback = filePathCallback
                            launcher.launch(IMAGE_TYPE)
                            return true
                        }


                        @SuppressLint("SetJavaScriptEnabled")
                        override fun onCreateWindow(
                            view: WebView?, isDialog: Boolean,
                            isUserGesture: Boolean, resultMsg: Message
                        ): Boolean {
                            val newWebView = WebView(context)
                            newWebView.settings.javaScriptEnabled = true
                            newWebView.webChromeClient = this
                            newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                            newWebView.settings.domStorageEnabled = true
                            newWebView.settings.setSupportMultipleWindows(true)
                            val transport = resultMsg.obj as WebView.WebViewTransport
                            transport.webView = newWebView
                            resultMsg.sendToTarget()
                            return true
                        }
                    }
                }
            }, update = {
                lepryBrewWebView = it
            })
        }
    }

    private fun startLepryGame() {
        val i = Intent(this, LepryGame::class.java)
        startActivity(i)
        finish()
    }

    companion object {
        private const val IMAGE_TYPE = "image/*"
    }
}