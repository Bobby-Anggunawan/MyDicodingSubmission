import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.ChainLizard.altis.R

class HomeListAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<HomeListAdapter.ItemData_Holder>() {

    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var gambar: Int,
        var nama: String,
        var takson: String,
        var diet: String,
        var kesulitan: String,
        var link: String
    )

    var onItemClick: ((HomeListAdapter.RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View):RecyclerView.ViewHolder(ItemLayout){
        var gambar: ImageView = ItemLayout.findViewById(R.id.imageView)
        var nama: TextView = ItemLayout.findViewById(R.id.textView)
        var takson: TextView = ItemLayout.findViewById(R.id.takson)
        var diet: TextView = ItemLayout.findViewById(R.id.diet)
        var difficulty: TextView = ItemLayout.findViewById(R.id.difficulty)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeListAdapter.ItemData_Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_home_list, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: HomeListAdapter.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        holder.gambar.setImageResource(An_Item.gambar)
        holder.nama.text = An_Item.nama
        holder.takson.text = An_Item.takson
        holder.diet.text = An_Item.diet
        holder.difficulty.text = An_Item.kesulitan
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}