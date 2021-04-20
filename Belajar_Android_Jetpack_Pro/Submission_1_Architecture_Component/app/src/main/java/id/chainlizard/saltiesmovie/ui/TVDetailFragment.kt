package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import id.chainlizard.saltiesmovie.MainActivity
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.myIdlingResource
import id.chainlizard.saltiesmovie.viewmodel.MovieDetailVM
import id.chainlizard.saltiesmovie.viewmodel.TVDetailVM

class TVDetailFragment : Fragment() {

    lateinit var judul: TextView
    lateinit var poster: ImageView
    lateinit var tagline: TextView
    lateinit var overview: TextView
    lateinit var rating: RatingBar
    lateinit var tanggalRilis: TextView
    lateinit var tanggalTamat: TextView
    lateinit var season: TextView
    lateinit var episodes: TextView
    lateinit var bahasa: TextView
    lateinit var tipe: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_t_v_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        judul = view.findViewById(R.id.Judul)
        poster = view.findViewById(R.id.poster)
        tagline = view.findViewById(R.id.tagline)
        overview = view.findViewById(R.id.overview)
        rating = view.findViewById(R.id.rating)
        tanggalRilis = view.findViewById(R.id.tanggalMulai)
        tanggalTamat = view.findViewById(R.id.tanggalTamat)
        season = view.findViewById(R.id.season)
        episodes = view.findViewById(R.id.jlhEpisode)
        bahasa = view.findViewById(R.id.language)
        tipe = view.findViewById(R.id.type)

        val TVID = MyObj.readIdPreference(requireActivity())

        val model: TVDetailVM by viewModels()
        model.getTV(TVID).observe(requireActivity(), {
            Glide.with(requireActivity()).load("https://www.themoviedb.org/t/p/w220_and_h330_face"+it.poster_path).into(poster)

            judul.text = it.name
            tagline.text = it.tagline
            overview.text = it.overview
            rating.rating = it.vote_average.toFloat()
            tanggalRilis.text = it.first_air_date
            tanggalTamat.text = it.last_air_date
            bahasa.text = it.original_language
            season.text = it.number_of_seasons.toString()
            episodes.text = it.number_of_episodes.toString()
            tipe.text = it.type

            myIdlingResource.decrement()
        })
    }
}