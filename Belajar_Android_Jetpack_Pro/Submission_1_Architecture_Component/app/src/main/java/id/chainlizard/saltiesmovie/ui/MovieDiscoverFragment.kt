package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.functions.SetUpRecyclerView
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.viewmodel.MovieDiscoverVM

@AndroidEntryPoint
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

        myRecyclerView = view.findViewById(R.id.movieList)
        val model: MovieDiscoverVM by viewModels()
        model.getMovie(requireContext()).observe(requireActivity(), {
            if(myRecyclerView.adapter == null){
                SetUpRecyclerView.setUp(it).recyclerView(myRecyclerView)
                    .setListItemLayout(R.layout.item_list_discover)
                    .setActivity(requireActivity())
                    .addImageView("poster_path", R.id.poster)
                    .addTextView("original_title", R.id.judul)
                    .addTextView("overview", R.id.overview)
                    .addRatingBar("vote_average", R.id.rating)
                    .setItemOnClick_ThenRun {
                        MyObj.writeIdPreference(it.id, requireActivity())
                        findNavController().navigate(R.id.fragment_movie_detail)
                    }
            }
            myRecyclerView.adapter?.notifyDataSetChanged()

            MyIdlingResource.decrement()
        })
    }

}