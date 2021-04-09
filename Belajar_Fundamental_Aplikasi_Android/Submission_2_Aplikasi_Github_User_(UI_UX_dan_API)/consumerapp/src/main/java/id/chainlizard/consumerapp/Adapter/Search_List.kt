package id.chainlizard.consumerapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.chainlizard.consumerapp.R
import id.chainlizard.consumerapp.TypeList

class Search_List(val Data: ArrayList<TypeList.User>): RecyclerView.Adapter<Search_List.ItemData_Holder>() {
    var onItemClick: ((TypeList.User) -> Unit)? = null

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
        Glide.with(holder.itemView.context).load(An_Item.avatar_url).into(holder.avatar);
        holder.username.text = An_Item.login
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}