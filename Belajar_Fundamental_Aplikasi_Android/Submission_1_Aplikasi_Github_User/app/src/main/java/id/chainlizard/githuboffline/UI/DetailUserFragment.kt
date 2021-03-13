package id.chainlizard.githuboffline.UI

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.google.android.material.appbar.MaterialToolbar
import id.chainlizard.githuboffline.Database
import id.chainlizard.githuboffline.MainViewModel
import id.chainlizard.githuboffline.R
import id.chainlizard.githuboffline.UserListAdapter
import java.lang.Exception


class DetailUserFragment : Fragment() {

    lateinit var bndl: Bundle
    lateinit var usr: UserListAdapter.RowData
    lateinit var topAppBar: MaterialToolbar


    companion object{
        lateinit var switchBtn: Switch
        lateinit var mainViewModel: MainViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_detail_user, container, false)

        bndl = this.requireArguments()
        usr = bndl.getParcelable<UserListAdapter.RowData>("key")!!
        topAppBar = root.findViewById(R.id.topAppBar)

        topAppBar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        switchBtn = topAppBar.findViewById(R.id.switch1)

        //init switch
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.getUser().observe(viewLifecycleOwner, Observer<Boolean>{ users ->
            switchBtn.isChecked = users
        })
        val thr1 = InitSwitch(requireContext(), usr.username)
        thr1.start()

        //isi data
        topAppBar.title = usr.username
        root.findViewById<TextView>(R.id.textView).text = usr.name
        root.findViewById<TextView>(R.id.follower).text = usr.follower.toString()
        root.findViewById<TextView>(R.id.following).text = usr.following.toString()
        root.findViewById<TextView>(R.id.repo).text = usr.repository.toString()
        root.findViewById<TextView>(R.id.location).text = usr.location
        root.findViewById<TextView>(R.id.work).text = usr.company
        val uri = Uri.parse("android.resource://id.chainlizard.githuboffline/drawable/${usr.avatar.substring(9)}")
        root.findViewById<ImageView>(R.id.circleImageView).setImageURI(uri)


        return root
    }

    class InitSwitch(val cntx: Context, val username: String): Thread(){
        override fun run() {
            super.run()
            val db = Room.databaseBuilder(
                cntx,
                Database.AppDatabase::class.java, "github_user"
            ).build()
            val userDao = db.userDao()
            val alist = userDao.getAll()
            if(alist.count()>0){
                var ret: Boolean = false
                for(x in 0..alist.count()-1){
                    if(alist[x].uid == username){
                        ret = true
                        break
                    }
                }
                mainViewModel.setUser(ret)
            }

            switchBtn.setOnCheckedChangeListener { buttonView, isChecked ->
                val thr = GetFavThread(isChecked, cntx, username)
                thr.start()
            }
        }
    }

    class GetFavThread(val isChecked: Boolean, val cntx: Context, val username: String): Thread() {
        public override fun run() {
            if(isChecked){
                val db = Room.databaseBuilder(
                    cntx,
                    Database.AppDatabase::class.java, "github_user"
                ).build()
                val userDao = db.userDao()
                try{
                    userDao.insertAll(Database.User(username))
                }
                catch (e: Exception){
                    Log.e("my", e.message.toString())
                }
            }
            else{
                val db = Room.databaseBuilder(
                    cntx,
                    Database.AppDatabase::class.java, "github_user"
                ).build()
                val userDao = db.userDao()
                userDao.delete(Database.User(username))
            }
        }
    }
}