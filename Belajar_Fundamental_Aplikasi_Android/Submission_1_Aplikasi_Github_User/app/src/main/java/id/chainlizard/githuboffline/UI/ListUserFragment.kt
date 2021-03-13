package id.chainlizard.githuboffline.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.chainlizard.githuboffline.MainActivity
import id.chainlizard.githuboffline.R
import id.chainlizard.githuboffline.UserListAdapter


class ListUserFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_list_user, container, false)
        myRecyclerView = root.findViewById(R.id.listView)
        SetAdapter()
        return root
    }

    fun SetAdapter(){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = UserListAdapter(MainActivity.alist)
        myRecyclerView.adapter = ListAdapter

        ListAdapter.onItemClick = {
            val bndl = Bundle()
            bndl.putParcelable("key", it)
            MainActivity.navController.navigate(R.id.fragment_detail, bndl)
        }
    }

}