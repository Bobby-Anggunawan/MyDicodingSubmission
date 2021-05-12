package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.viewmodel.MovieDetailVM

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    lateinit var judul: TextView
    lateinit var poster: ImageView
    lateinit var tagline: TextView
    lateinit var overview: TextView
    lateinit var rating: RatingBar
    lateinit var tanggal: TextView
    lateinit var bahasa: TextView
    lateinit var status: TextView
    lateinit var badget: TextView
    lateinit var revenue: TextView

    lateinit var mySpin: ProgressBar
    lateinit var favBtn: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        judul = view.findViewById(R.id.Judul)
        poster = view.findViewById(R.id.poster)
        tagline = view.findViewById(R.id.tagline)
        overview = view.findViewById(R.id.overview)
        rating = view.findViewById(R.id.rating)
        tanggal = view.findViewById(R.id.tanggal)
        bahasa = view.findViewById(R.id.language)
        status = view.findViewById(R.id.status)
        badget = view.findViewById(R.id.budget)
        revenue = view.findViewById(R.id.revenue)

        mySpin = view.findViewById(R.id.mySpin)
        favBtn = view.findViewById(R.id.favorit)

        val movieID = MyObj.readIdPreference(requireActivity())

        val model: MovieDetailVM by viewModels()
        model.getMovie(movieID, requireContext(), mySpin).observe(requireActivity(), {
            Glide.with(requireActivity())
                .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + it.poster_path)
                .into(poster)

            judul.text = it.title
            tagline.text = it.tagline
            overview.text = it.overview
            rating.rating = it.vote_average.toFloat()
            tanggal.text = it.release_date
            bahasa.text = it.original_language
            status.text = it.status
            badget.text = it.budget.toString()
            revenue.text = it.revenue.toString()

            mySpin.visibility = View.GONE
        })
        model.getFavorit(movieID).observe(requireActivity(), {
            if(it == true){
                favBtn.setImageResource(R.drawable.ic_favorite_white_18dp)
            }
            else{
                favBtn.setImageResource(R.drawable.ic_favorite_border_white_18dp)
            }
        })
        favBtn.setOnClickListener {
            model.postFavorit(movieID)
        }
    }

}