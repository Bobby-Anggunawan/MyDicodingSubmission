package id.chainlizard.githubsearch.UI.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.UI.MainActivity
import id.chainlizard.githubsearch.ViewModel.Favorite

class FavoriteFragment : Fragment() {

    companion object{
        lateinit var model: Favorite
    }
    private lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_favorite, container, false)

        root.findViewById<MaterialToolbar>(R.id.topAppBar).setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        myRecyclerView = root.findViewById(R.id.recyclerFavorite)
        val alist = arrayListOf<TypeList.User>()
        SetAdapter(alist)

        model.getUsers(requireContext()).observe(requireActivity(), { users ->
            alist.clear()
            alist.addAll(users)
            myRecyclerView.adapter?.notifyDataSetChanged()
        })

        return root
    }

    fun SetAdapter(users: ArrayList<TypeList.User>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Search_List(users)
        myRecyclerView.adapter = ListAdapter

        ListAdapter.onItemClick = {
            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
            with(sharedPref!!.edit()) {
                putString("UserName", it.login)
                apply()
            }
            MainActivity.navController.navigate(R.id.fragment_detail)
        }
    }
}