package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.SetUpTabView

@AndroidEntryPoint
class ContainerMovieAndFilmFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_container_movie_and_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        val ttl = arrayOf("Movie", "Tv")
        val alist = arrayListOf(
            MovieDiscoverFragment(MyObj.pageType.Discover),
            TVDiscoverFragment(MyObj.pageType.Discover)
        )
        SetUpTabView.setUp(requireActivity() as AppCompatActivity, viewPager, tabs, 2, ttl, alist)

        view.findViewById<ExtendedFloatingActionButton>(R.id.extended_fab).setOnClickListener {
            findNavController().navigate(R.id.fragment_favorit)
        }
    }
}