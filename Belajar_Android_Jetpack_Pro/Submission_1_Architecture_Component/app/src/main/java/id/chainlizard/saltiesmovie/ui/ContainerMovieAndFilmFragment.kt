package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.SetUpTabView


class ContainerMovieAndFilmFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_container_movie_and_film, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        val tabs: TabLayout = view.findViewById(R.id.tabs)
        val ttl = arrayOf("Movie", "Tv")
        val alist = arrayListOf<Fragment>(MovieDiscoverFragment(), TVDiscoverFragment())
        SetUpTabView.setUp(requireActivity() as AppCompatActivity, viewPager, tabs, 2, ttl, alist)
    }
}