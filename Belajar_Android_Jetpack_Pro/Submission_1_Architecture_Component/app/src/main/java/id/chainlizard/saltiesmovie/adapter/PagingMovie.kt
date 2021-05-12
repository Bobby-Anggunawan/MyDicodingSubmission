package id.chainlizard.saltiesmovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.chainlizard.saltiesmovie.data.model.MovieDiscoverMod
import id.chainlizard.saltiesmovie.databinding.ItemListDiscoverBinding

object PagingMovie {
    object ItemComparator : DiffUtil.ItemCallback<MovieDiscoverMod.MoviePage_List>() {
        override fun areItemsTheSame(oldItem: MovieDiscoverMod.MoviePage_List, newItem: MovieDiscoverMod.MoviePage_List): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: MovieDiscoverMod.MoviePage_List, newItem: MovieDiscoverMod.MoviePage_List): Boolean {
            return oldItem == newItem
        }
    }

    class MyPagingAdapter(private val click: myItemClickListener): PagingDataAdapter<MovieDiscoverMod.MoviePage_List, MyPagingAdapter.MyViewHolder>(
        ItemComparator
    ){

        data class myItemClickListener(val clickListener: (data: MovieDiscoverMod.MoviePage_List) -> Unit)

        class MyViewHolder(private var binding: ItemListDiscoverBinding): RecyclerView.ViewHolder(binding.root){
            fun bind(item: MovieDiscoverMod.MoviePage_List, clickListener: (MovieDiscoverMod.MoviePage_List) -> Unit){
                binding.judul.text = item.original_title
                binding.overview.text = item.overview
                Glide.with(itemView)
                    .load("https://www.themoviedb.org/t/p/w220_and_h330_face" + item.poster_path)
                    .into(binding.poster)
                binding.rating.rating = item.vote_average.toFloat()

                itemView.setOnClickListener { clickListener(item) }
            }
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val currentItem = getItem(position)
            if(currentItem != null){
                holder.bind(currentItem, click.clickListener)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val binding = ItemListDiscoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(binding)
        }
    }
}