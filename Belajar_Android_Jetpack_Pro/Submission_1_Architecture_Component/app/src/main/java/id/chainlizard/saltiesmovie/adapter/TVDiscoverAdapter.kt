package id.chainlizard.saltiesmovie.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.chainlizard.saltiesmovie.R
import id.chainlizard.saltiesmovie.functions.MyObj
import id.chainlizard.saltiesmovie.model.TVDiscoverMod

class TVDiscoverAdapter(val Data: ArrayList<TVDiscoverMod.TVPage_List>): RecyclerView.Adapter<TVDiscoverAdapter.ItemData_Holder>() {
    companion object{
        fun SetAdapter(data: ArrayList<TVDiscoverMod.TVPage_List>, myRecyclerView: RecyclerView, activity: Activity, navController: NavController){
            myRecyclerView.layoutManager = LinearLayoutManager(activity)
            val ListAdapter = TVDiscoverAdapter(data)
            ListAdapter.onItemClick = {
                MyObj.writeIdPreference(it.id, activity)
                navController.navigate(R.id.fragment_t_v_detail)
            }
            myRecyclerView.adapter = ListAdapter
        }
    }

    var onItemClick: ((TVDiscoverMod.TVPage_List) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var poster: ImageView = ItemLayout.findViewById(R.id.poster)
        var judul: TextView = ItemLayout.findViewById(R.id.judul)
        var overview: TextView = ItemLayout.findViewById(R.id.overview)
        var rating: RatingBar = ItemLayout.findViewById(R.id.rating)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVDiscoverAdapter.ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_discover, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: TVDiscoverAdapter.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        Glide.with(holder.itemView.context).load("https://www.themoviedb.org/t/p/w220_and_h330_face"+An_Item.poster_path).into(holder.poster)
        holder.judul.text = An_Item.name
        holder.overview.text = An_Item.overview
        holder.rating.rating = An_Item.vote_average.toFloat()
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}