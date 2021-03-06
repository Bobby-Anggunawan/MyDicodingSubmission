package id.chainlizard.githubsearch.UI.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.ybq.android.spinkit.SpinKitView
import com.github.ybq.android.spinkit.style.DoubleBounce
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.chainlizard.githubsearch.Adapter.Detail_List
import id.chainlizard.githubsearch.R
import id.chainlizard.githubsearch.TypeList
import id.chainlizard.githubsearch.ViewModel.Detail


class DetailFragment : Fragment() {

    private lateinit var myRecyclerView: RecyclerView
    private lateinit var mySpinKit: SpinKitView
    private lateinit var myToolBar: MaterialToolbar
    private lateinit var myFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_detail, container, false)
        val usrName = TypeList.readSharedPreference(requireActivity(), "UserName", "bobby")

        myRecyclerView = root.findViewById(R.id.more_detail)
        mySpinKit = root.findViewById(R.id.spin_kit)
        myToolBar = root.findViewById(R.id.topAppBar)
        val mySprite = DoubleBounce()
        mySpinKit.setIndeterminateDrawable(mySprite)

        myToolBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }
        myFab = root.findViewById(R.id.myFab)
        //mengatur backdrop
        val backdrop: LinearLayout = root.findViewById(R.id.backdrop)
        val sheetBehavior = BottomSheetBehavior.from(backdrop)
        sheetBehavior.setFitToContents(false)
        sheetBehavior.setHideable(false)
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)

        val model: Detail by viewModels()
        myToolBar.title = usrName
        model.getUser("https://api.github.com/users/"+ usrName).observe(requireActivity(), {
            try{
                // todo(kadang error gak tahu kenapa.. katanya karena requireContext(), tapi saya test meski errornya diabaikan tidak ada masalah yang muncul)
                Glide.with(requireContext()).load(it.usr.avatar_url).into(root.findViewById(R.id.detail_avatar))
            }
            catch (e: Exception){

            }
            root.findViewById<TextView>(R.id.repo).text = it.usr.public_repos.toString()
            root.findViewById<TextView>(R.id.follower).text = it.usr.followers.toString()
            root.findViewById<TextView>(R.id.following).text = it.usr.following.toString()
            if(!it.usr.bio.isNullOrEmpty()){
                root.findViewById<TextView>(R.id.bio).text = it.usr.bio
            }
            else{
                root.findViewById<TextView>(R.id.bio).visibility = View.GONE
            }
            if(it.usr.name.isNullOrEmpty()){
                root.findViewById<TextView>(R.id.nama).text = it.usr.login
            }
            else{
                root.findViewById<TextView>(R.id.nama).text = it.usr.name
            }
            SetAdapter(it.detail)
            mySpinKit.visibility = View.INVISIBLE
        })
        model.getFabState(requireContext(), usrName).observe(requireActivity(), {
            //ubah icon di fab ketika click(kayak toggle button)
            if(it == false){
                myFab.setImageResource(R.drawable.ic_favorite_border_white_18dp)
            }
            else{
                myFab.setImageResource(R.drawable.ic_favorite_white_18dp)
            }
        })

        myFab.setOnClickListener{
            model.fabChangeState(requireContext(), model.returnUser())
        }

        myToolBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.share -> {
                    val user = model.returnUser()
                    val sendText = """Name: ${user.name}
                        |Github username: ${user.login}
                        |Github follower: ${user.followers}
                        |Github following: ${user.following}
                        |Github repository: ${user.public_repos}
                    """.trimMargin()
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, sendText)
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                    true
                }
                else -> false
            }
        }

        return root
    }

    fun SetAdapter(detail: ArrayList<Detail_List.RowData>){
        myRecyclerView.layoutManager = LinearLayoutManager(getActivity())
        val ListAdapter = Detail_List(detail)
        myRecyclerView.adapter = ListAdapter
    }
}