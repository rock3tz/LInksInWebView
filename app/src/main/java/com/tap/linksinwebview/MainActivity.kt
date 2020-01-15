package com.tap.linksinwebview

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.View.OnKeyListener
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private var webview: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetJavaScriptEnabled", "SetTextI18n")
    override fun onResume() {
        super.onResume()

        webview = findViewById(R.id.webview)
        val urlText = findViewById<TextView>(R.id.urlText)
        urlText.text = "https://intranet.quality.tap.pt/"
        urlText.setOnKeyListener(object : OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (event!!.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    webview!!.loadUrl(urlText.text.toString())
                    return true
                }
                return false
            }
        })

        webview!!.settings.javaScriptEnabled = true
        webview!!.webViewClient = WebViewClient()
        webview!!.loadUrl(urlText.text.toString())

        findViewById<Button>(R.id.goBtn).setOnClickListener {
            webview!!.loadUrl(urlText.text.toString())
            urlText.hideKeyboard()
        }
        findViewById<Button>(R.id.backBtn).setOnClickListener { webview!!.goBack() }

        //  more complect client to decide which links open in browser
        /*webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }*/
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onBackPressed() {
        if(webview!!.canGoBack()) {
            webview!!.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
