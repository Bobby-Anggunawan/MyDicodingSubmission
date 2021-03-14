package id.chainlizard.githubsearch.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.chainlizard.githubsearch.R

class Search_List(val Data: ArrayList<RowData>): RecyclerView.Adapter<Search_List.ItemData_Holder>() {
    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var avatar: String,
        var username: String
    )

    var onItemClick: ((Search_List.RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var avatar: ImageView = ItemLayout.findViewById(R.id.avatar)
        var username: TextView = ItemLayout.findViewById(R.id.username)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Search_List.ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: Search_List.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        Glide.with(holder.itemView.context).load(An_Item.avatar).into(holder.avatar);
        holder.username.text = An_Item.username
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}