package id.chainlizard.githubsearch.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.chainlizard.githubsearch.R

class Detail_List(val Data: ArrayList<RowData>): RecyclerView.Adapter<Detail_List.ItemData_Holder>() {
    enum class JenisField{
        company, blog, location, email, bio, twitter
    }
    data class RowData(
            var isi: String,
            var jenis: JenisField
    )

    var onItemClick: ((RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var logo: ImageView = ItemLayout.findViewById(R.id.detail_icon)
        var isi: TextView = ItemLayout.findViewById(R.id.detail_content)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Detail_List.ItemData_Holder {
        val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: Detail_List.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        when(An_Item.jenis){
            JenisField.company -> Glide.with(holder.itemView.context).load(R.drawable.ic_work_black_18dp).into(holder.logo)
            JenisField.blog -> Glide.with(holder.itemView.context).load(R.drawable.ic_public_black_18dp).into(holder.logo)
            JenisField.location -> Glide.with(holder.itemView.context).load(R.drawable.ic_room_24px).into(holder.logo)
            JenisField.email -> Glide.with(holder.itemView.context).load(R.drawable.ic_email_black_18dp).into(holder.logo)
            JenisField.bio -> holder.logo.visibility = View.GONE
            JenisField.twitter -> Glide.with(holder.itemView.context).load(R.drawable.ic_twitter).into(holder.logo)
        }
        holder.isi.text = An_Item.isi
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}