package id.chainlizard.githubsearch.UI

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elyeproj.loaderviewlibrary.LoaderImageView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.chainlizard.githubsearch.Adapter.Detail_List
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.ViewModel.Detail
import id.chainlizard.githubsearch.ViewModel.Search


class DetailFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView
    data class User(
            var avatar: String,
            var username: String,
            var nama: String,
            var follower: Int,
            var following: Int,
            var repo: Int,
            var company: String?,
            var blog: String?,
            var location: String?,
            var email: String?,
            var bio: String?,
            var twitter: String?
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        myRecyclerView = root.findViewById(R.id.more_detail)
        //mengatur backdrop
        val backdrop: LinearLayout = root.findViewById(R.id.backdrop)
        val sheetBehavior = BottomSheetBehavior.from(backdrop)
        sheetBehavior.setFitToContents(false)
        sheetBehavior.setHideable(false)
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

        //view model
        val model: Detail by viewModels()
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val usrName = sharedPref?.getString("UserName", "bobby")
        model.getUser("https://api.github.com/users/"+ usrName).observe(requireActivity(), Observer<User> {
            Glide.with(requireContext()).load(it.avatar).into(root.findViewById(R.id.detail_avatar))
            root.findViewById<TextView>(R.id.repo).text = it.repo.toString()
            root.findViewById<TextView>(R.id.follower).text = it.follower.toString()
            root.findViewById<TextView>(R.id.following).text = it.following.toString()
            root.findViewById<TextView>(R.id.nama).text = it.nama
        })

        return root
    }

    fun SetAdapter(detail: ArrayList<Detail_List.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Detail_List(detail) //arraylist berisi data
        myRecyclerView.adapter = ListAdapter

        //mengatur onclick tiap item
        ListAdapter.onItemClick = {
            MainActivity.navController.navigate(R.id.fragment_detail)
        }
    }
}