package id.chainlizard.saltiesmovie.functions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

object SetUpTabView {
    class MyPager(activity: AppCompatActivity, pageCount: Int, pageList: ArrayList<Fragment>) :
        FragmentStateAdapter(activity) {
        var numPage: Int = 0
        var fragmentList: ArrayList<Fragment> = arrayListOf()

        init {
            numPage = pageCount
            fragmentList.addAll(pageList)
        }

        override fun createFragment(position: Int): Fragment {
            val fragment: Fragment = fragmentList[position]
            return fragment
        }

        override fun getItemCount(): Int {
            return numPage
        }
    }

    fun setUp(
        activity: AppCompatActivity,
        myViewPager: ViewPager2,
        myTabLayout: TabLayout,
        numPage: Int,
        titleEveryPage: Array<String>,
        pageList: ArrayList<Fragment>
    ) {
        val sectionsPagerAdapter = MyPager(activity, numPage, pageList)
        myViewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(myTabLayout, myViewPager) { tab, position ->
            tab.text = titleEveryPage[position]
        }.attach()
    }
}