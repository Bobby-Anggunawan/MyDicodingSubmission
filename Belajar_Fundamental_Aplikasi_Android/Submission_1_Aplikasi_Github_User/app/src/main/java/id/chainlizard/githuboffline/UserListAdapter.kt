package id.chainlizard.githuboffline

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserListAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<UserListAdapter.ItemData_Holder>() {
    data class RowData(
        var username: String,
        var name: String,
        var avatar: String,
        var company: String,
        var location: String,
        var repository: Int,
        var follower: Int,
        var following: Int
    ):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(username)
            parcel.writeString(name)
            parcel.writeString(avatar)
            parcel.writeString(company)
            parcel.writeString(location)
            parcel.writeInt(repository)
            parcel.writeInt(follower)
            parcel.writeInt(following)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<RowData> {
            override fun createFromParcel(parcel: Parcel): RowData {
                return RowData(parcel)
            }

            override fun newArray(size: Int): Array<RowData?> {
                return arrayOfNulls(size)
            }
        }
    }

    var onItemClick: ((UserListAdapter.RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var avatar: ImageView = ItemLayout.findViewById(R.id.avatar)
        var username: TextView = ItemLayout.findViewById(R.id.username)
        var name: TextView = ItemLayout.findViewById(R.id.nama)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListAdapter.ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: UserListAdapter.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        val uri = Uri.parse("android.resource://id.chainlizard.githuboffline/drawable/${An_Item.avatar.substring(9)}")
        holder.avatar.setImageURI(uri)
        holder.username.text = An_Item.username
        holder.name.text = An_Item.name
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}