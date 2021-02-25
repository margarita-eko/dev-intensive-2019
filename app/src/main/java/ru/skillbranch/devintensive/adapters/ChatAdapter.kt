package ru.skillbranch.devintensive.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatAdapter() : RecyclerView.Adapter<ChatAdapter.SingleViewHolder>() {

    var items: List<ChatItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder:ChatAdapter.SingleViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class SingleViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) ,LayoutContainer {

        override val containerView: View? get() = itemView

        val iv_avatar = convertView.findViewById<ImageView>(R.id.iv_avatar_single)
        val tv_title = convertView.findViewById<TextView>(R.id.tv_title_single)
        val sv_indicator = convertView.findViewById<ImageView>(R.id.sv_indicator)

        fun bind(item: ChatItem) {
            //iv_avatar.
            tv_title.text = item.shortDescription
            sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
            with(tv_date_single){
                visibility = if(item.lastMessageDate!=null) View.VISIBLE else View.GONE
                text = item.lastMessageDate
            }

            with(tv_counter_single){
                visibility = if(item.messageCount>0) View.VISIBLE else View.GONE
                text = item.messageCount.toString()
            }

            tv_title_single.text = item.title
            tv_message_single.text = item.shortDescription
        }
    }

    fun updateData(data: List<ChatItem>){
        items = data
        notifyDataSetChanged()

    }

}