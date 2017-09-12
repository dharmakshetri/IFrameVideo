package co.prandroid.soccerlivehd

import android.os.Build
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*


class Main2Activity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_youtube -> {
                loadVideo(getString(R.string.title_youtube),Urls.YOUTUBE_URL)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_soccer -> {
                loadVideo(getString(R.string.title_soccer),Urls.SOCCER_URL)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_cricket -> {
                loadVideo(getString(R.string.title_cricket),Urls.CRICKET_URL)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_Graph -> {
                loadVideo(getString(R.string.title_Graph),Urls.GRAPH_URL)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadVideo( title:String,  url:String) {
        message.text=title

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        webSettings.allowFileAccess = true

        if (Build.VERSION.SDK_INT > 7) {
            webSettings.pluginState = WebSettings.PluginState.ON
        } else {
            webSettings.pluginState
        }


        val activity = this
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000)
            }
        }
        webView.webViewClient = object : WebViewClient() {
            override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
                Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show()
            }
        }

        webView.loadData(url, "text/html", null)
        webView.webViewClient = WebViewClient()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
    override fun onResume() {
        super.onResume()
        try {
            WebView::class.java.getMethod("onResume").invoke(webView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onPause() {
        super.onPause()
        try {
            WebView::class.java.getMethod("onPause").invoke(webView)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}
