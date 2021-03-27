package id.chainlizard.githubsearch.UI

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.CubeGrid
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.github.ybq.android.spinkit.style.Pulse
import com.github.ybq.android.spinkit.style.WanderingCubes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.Networking
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.ViewModel.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var mySpinKit: SpinKitView
    lateinit var mySearch: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val model: Search by viewModels()
        myRecyclerView = root.findViewById(R.id.user_list)
        mySpinKit = root.findViewById(R.id.spin_kit)
        mySearch = root.findViewById(R.id.search_bar)
        val mySprite = CubeGrid()
        mySpinKit.setIndeterminateDrawable(mySprite)
        val alist = arrayListOf<Search_List.RowData>()
        SetAdapter(alist)

        //view model
        model.getUsers(Search.jsonType.follow).observe(requireActivity(), Observer<ArrayList<Search_List.RowData>>{ users ->
            alist.clear()
            alist.addAll(users)
            mySpinKit.visibility = View.INVISIBLE
            myRecyclerView.adapter?.notifyDataSetChanged()
        })
        //view
        mySearch.setOnQueryTextListener(object: OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String?): Boolean {
                if(!(query.isNullOrBlank() || query.isEmpty())){
                    alist.clear()
                    myRecyclerView.adapter!!.notifyDataSetChanged()
                    mySpinKit.visibility = View.VISIBLE
                    GlobalScope.launch(Dispatchers.Default){
                        query?.let { model.loadUsers(Search.jsonType.search,"https://api.github.com/search/users?q="+it) }
                    }
                }
                return true
            }
        })
        return root
    }

    fun SetAdapter(users: ArrayList<Search_List.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Search_List(users) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter

        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with (sharedPref!!.edit()) {
                putString("UserName", it.username)
                apply()
            }
            context?.let { it1 -> closeSoftKeyboard(it1, mySearch) }
            MainActivity.navController.navigate(R.id.fragment_detail)
        }
    }

    fun closeSoftKeyboard(context: Context, v: View) {
        val iMm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        iMm.hideSoftInputFromWindow(v.windowToken, 0)
        v.clearFocus()
    }
}