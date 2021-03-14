package id.chainlizard.githubsearch.UI.Follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.chainlizard.githubsearch.Adapter.Follow_Pager
import id.chainlizard.githubsearch.R

class MainFragment : Fragment() {

    val TAB_TITLES = arrayOf(
            "Follower",
            "Following"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main, container, false)

        val sectionsPagerAdapter = Follow_Pager(requireActivity() as AppCompatActivity)
        val viewPager: ViewPager2 = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()
        return root
    }
}