package com.folliedimomi.fragment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.OnTouchListener
import android.webkit.*
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.folliedimomi.R
import com.folliedimomi.interfaces.IOnBackPressed

import kotlinx.android.synthetic.main.fragment_web_page.*

class WebPageFragment(private val url: String) : Fragment(), IOnBackPressed {

    override fun onBackPressed() {
        /*if (webView != null && webView.canGoBack()) {
            webView.goBack()
            true
        } else {
            //requireActivity().onBackPressed()
            false
        }*/

        /*if (webView.canGoBack()) {
            webView.goBack()
        } else{
            requireActivity().onBackPressed()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_web_page, container, false)
    }

    private var m_downX = 0f
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        webView.loadUrl(url)

        /*requireActivity().onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView != null && webView.canGoBack()) {
                    webView.goBack()
                } else {
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            }
        })*/
    }

    private fun initView() {
        initWebView(webView, progressBar)
        swipeToRefresh.setOnRefreshListener {
            webView.loadUrl(url)
            swipeToRefresh.isRefreshing = false
        }
    }

    /**
     * WebView initialization at one point
     */
    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    fun initWebView(webView: WebView, progressBar: ProgressBar) {
        webView.webChromeClient = MyWebChromeClient(/*this@WebPageFragment.requireContext()*/)
        webView.webViewClient = MyWebViewClient(progressBar)
        webView.clearCache(true)
        webView.clearHistory()
        webView.settings.javaScriptEnabled = true
        webView.isHorizontalScrollBarEnabled = false
        webView.setOnTouchListener(OnTouchListener { v, event ->
            if (event.pointerCount > 1) { //Multi touch detected
                return@OnTouchListener true
            }
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // save the x
                    m_downX = event.x
                }
                MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                    // set x so that it doesn't move
                    event.setLocation(m_downX, event.y)
                }
            }
            false
        })
    }

    /**
     * WebView Chrome client setup
     */
    private class MyWebChromeClient() : WebChromeClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if (webView != null && webView.canGoBack()) {
                    webView.goBack()
                } else {
                    requireActivity().onBackPressed()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    class MyWebViewClient internal constructor(private val progressBar: ProgressBar) :
        WebViewClient() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val url: String = request?.url.toString();
            view?.loadUrl(url)
            return true
        }

        override fun shouldOverrideUrlLoading(webView: WebView, url: String): Boolean {
            webView.loadUrl(url)
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            progressBar.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }

        override fun onReceivedError(
            view: WebView,
            request: WebResourceRequest,
            error: WebResourceError
        ) {
            super.onReceivedError(view, request, error)
            progressBar.visibility = View.GONE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        System.gc()
    }
}

