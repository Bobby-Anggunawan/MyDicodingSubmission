package id.chainlizard.githubsearch.UI

import android.content.Context
import android.inputmethodservice.Keyboard
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.elyeproj.loaderviewlibrary.LoaderImageView
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import id.chainlizard.githubsearch.Adapter.Detail_List
import id.chainlizard.githubsearch.Adapter.Search_List
import id.chainlizard.githubsearch.MainActivity
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.ViewModel.Detail
import id.chainlizard.githubsearch.ViewModel.Search


class DetailFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var mySpinKit: SpinKitView
    lateinit var myToolBar: MaterialToolbar
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
        mySpinKit = root.findViewById(R.id.spin_kit)
        myToolBar = root.findViewById(R.id.topAppBar)
        val mySprite = DoubleBounce()
        mySpinKit.setIndeterminateDrawable(mySprite)

        myToolBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
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
        myToolBar.title = usrName
        model.getUser("https://api.github.com/users/"+ usrName).observe(requireActivity(), Observer<User> {
            Glide.with(requireContext()).load(it.avatar).into(root.findViewById(R.id.detail_avatar))
            root.findViewById<TextView>(R.id.repo).text = it.repo.toString()
            root.findViewById<TextView>(R.id.follower).text = it.follower.toString()
            root.findViewById<TextView>(R.id.following).text = it.following.toString()
            if(it.bio != "null"){
                root.findViewById<TextView>(R.id.bio).text = it.bio
            }
            else{
                root.findViewById<TextView>(R.id.bio).visibility = View.GONE
            }
            if(it.nama == "null"){
                root.findViewById<TextView>(R.id.nama).text = it.username
            }
            else{
                root.findViewById<TextView>(R.id.nama).text = it.nama
            }

            //load detail list
            SetAdapter(model.detailToArray(it))
            mySpinKit.visibility = View.INVISIBLE
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