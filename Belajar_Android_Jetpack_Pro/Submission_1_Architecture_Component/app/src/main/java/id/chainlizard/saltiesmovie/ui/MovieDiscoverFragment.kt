package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import id.chainlizard.saltiesmovie.MainActivity
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.adapter.MovieDiscoverAdapter
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.myIdlingResource
import id.chainlizard.saltiesmovie.viewmodel.MovieDiscoverVM

class MovieDiscoverFragment : Fragment() {

    lateinit var myRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myIdlingResource.increment()

        myRecyclerView = view.findViewById(R.id.movieList)
        val model: MovieDiscoverVM by viewModels()
        model.getMovie().observe(requireActivity(), {
            if(myRecyclerView.adapter == null){
                MovieDiscoverAdapter.SetAdapter(it, myRecyclerView, requireActivity(), findNavController())
            }
            myRecyclerView.adapter?.notifyDataSetChanged()

            myIdlingResource.decrement()
        })
    }

}