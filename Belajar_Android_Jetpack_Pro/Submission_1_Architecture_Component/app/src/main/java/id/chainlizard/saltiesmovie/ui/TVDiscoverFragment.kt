package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.adapter.PagingTV
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.SetUpRecyclerView
import id.chainlizard.saltiesmovie.viewmodel.TVDiscoverVM

@AndroidEntryPoint
class TVDiscoverFragment(private val type: MyObj.pageType) : Fragment() {

    lateinit var myRecyclerView: RecyclerView
    lateinit var mySpin: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_t_v_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myRecyclerView = view.findViewById(R.id.tvList)
        mySpin = view.findViewById(R.id.mySpin)

        val myAdapter = PagingTV.MyPagingAdapter(PagingTV.MyPagingAdapter.myItemClickListener{
            MyObj.writeIdPreference(it.id, requireActivity())
            findNavController().navigate(R.id.fragment_t_v_detail)
        })
        myRecyclerView.setHasFixedSize(true)
        myRecyclerView.adapter = myAdapter
        myRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        if(type == MyObj.pageType.Discover){
            val model: TVDiscoverVM by viewModels()
            model.pagingItems.observe(viewLifecycleOwner, {
                if(mySpin.visibility != View.GONE){
                    mySpin.visibility = View.GONE
                }
                myAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            })
        }
    }
}