package id.chainlizard.consumerapp.UI.Fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.CubeGrid
import com.google.android.material.appbar.MaterialToolbar
import id.chainlizard.consumerapp.Adapter.Search_List
import id.chainlizard.consumerapp.R
import id.chainlizard.consumerapp.TypeList
import id.chainlizard.consumerapp.UI.MainActivity
import id.chainlizard.consumerapp.ViewModel.Search
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var mySpinKit: SpinKitView
    private lateinit var mySearch: SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_search, container, false)
        val model: Search by viewModels()
        myRecyclerView = root.findViewById(R.id.user_list)
        mySpinKit = root.findViewById(R.id.spin_kit)
        mySearch = root.findViewById(R.id.search_bar)
        val mySprite = CubeGrid()
        mySpinKit.setIndeterminateDrawable(mySprite)
        root.findViewById<MaterialToolbar>(R.id.topAppBar).setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    MainActivity.navController.navigate(R.id.fragment_favorite)
                    true
                }
                R.id.setting -> {
                    MainActivity.navController.navigate(R.id.fragment_setting)
                    true
                }
                else -> false
            }
        }
        val alist = arrayListOf<TypeList.User>()
        SetAdapter(alist)

        model.getUsers(Search.jsonType.follow).observe(requireActivity(), { users ->
            alist.clear()
            alist.addAll(users)
            mySpinKit.visibility = View.INVISIBLE
            myRecyclerView.adapter?.notifyDataSetChanged()
        })

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
                        query.let { model.loadUsers(Search.jsonType.search,"https://api.github.com/search/users?q="+it) }
                    }
                }
                return true
            }
        })
        return root
    }

    fun SetAdapter(users: ArrayList<TypeList.User>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Search_List(users)
        myRecyclerView.adapter = ListAdapter

        ListAdapter.onItemClick = {
            TypeList.writeSharedPreference(requireActivity(), "UserName", it.login.toString())
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