package id.ChainLizard.altis.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialSharedAxis
import id.ChainLizard.altis.R
import java.io.InputStream


class ArticleFragment : Fragment() {

    lateinit var webView: WebView
    companion object{
        var title: String = "-"
        var loadUrl: String = "-"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, /* forward= */ false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_article, container, false)
        root.findViewById<MaterialToolbar>(R.id.topAppBar).title = title
        webView = root.findViewById(R.id.web_view)
        //load txt
        webView.loadUrl(loadUrl)
        return root
    }
}