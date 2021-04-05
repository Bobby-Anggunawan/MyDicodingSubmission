package id.chainlizard.githubsearch.UI.Follow

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.ThreeBounce
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.ViewModel.Search


class FollowerFragment : Fragment() {

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var mySpinKit: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_follower, container, false)
        val model: Search by viewModels()
        myRecyclerView = root.findViewById(R.id.user_list)
        mySpinKit = root.findViewById(R.id.spin_kit)
        val mySprite = ThreeBounce()
        mySpinKit.setIndeterminateDrawable(mySprite)
        val alist = arrayListOf<TypeList.User>()
        SetAdapter(alist)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val usrName = sharedPref?.getString("UserName", "bobby").toString()

        model.getUsers(Search.jsonType.follow, "https://api.github.com/users/$usrName/followers").observe(requireActivity(), { users ->
            alist.clear()
            alist.addAll(users)
            mySpinKit.visibility = View.INVISIBLE
            myRecyclerView.adapter?.notifyDataSetChanged()
        })
        return root
    }

    fun SetAdapter(users: ArrayList<TypeList.User>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Search_List(users)
        myRecyclerView.adapter = ListAdapter
    }
}