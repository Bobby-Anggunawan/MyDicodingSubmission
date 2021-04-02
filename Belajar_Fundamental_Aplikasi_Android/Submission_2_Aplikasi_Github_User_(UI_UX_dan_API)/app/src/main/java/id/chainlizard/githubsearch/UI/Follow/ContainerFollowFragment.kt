package id.chainlizard.githubsearch.UI.Follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.chainlizard.githubsearch.Adapter.Follow_Pager
import id.chainlizard.githubsearch.R

class ContainerFollowFragment : Fragment() {

    val TAB_TITLES = arrayOf(
            R.string.follower,
            R.string.following
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_container_follow, container, false)

        val sectionsPagerAdapter = Follow_Pager(requireActivity() as AppCompatActivity)
        val viewPager: ViewPager2 = root.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = root.findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
        return root
    }
}