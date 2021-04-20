package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.chainlizard.saltiesmovie.MainActivity
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.adapter.TVDiscoverAdapter
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.myIdlingResource
import id.chainlizard.saltiesmovie.viewmodel.TVDiscoverVM

class TVDiscoverFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_v_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myRecyclerView = view.findViewById(R.id.tvList)
        val model: TVDiscoverVM by viewModels()
        model.getTV().observe(requireActivity(), {
            if(myRecyclerView.adapter == null){
                TVDiscoverAdapter.SetAdapter(it, myRecyclerView, requireActivity(), findNavController())
            }
            myRecyclerView.adapter?.notifyDataSetChanged()

            myIdlingResource.decrement()
        })
    }
}