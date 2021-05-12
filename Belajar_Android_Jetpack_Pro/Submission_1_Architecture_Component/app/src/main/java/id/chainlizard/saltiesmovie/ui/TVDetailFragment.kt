package id.chainlizard.saltiesmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyIdlingResource
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.viewmodel.TVDetailVM

@AndroidEntryPoint
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

    lateinit var mySpin: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

        mySpin = view.findViewById(R.id.mySpin)

        val TVID = MyObj.readIdPreference(requireActivity())

        val model: TVDetailVM by viewModels()
        model.getTV(TVID, requireContext(), mySpin).observe(requireActivity(), {
            Glide.with(requireActivity())
                .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + it.poster_path)
                .into(poster)

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

            mySpin.visibility = View.GONE
            MyIdlingResource.decrement()
        })
    }
}