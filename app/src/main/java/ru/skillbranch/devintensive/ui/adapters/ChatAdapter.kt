package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_single.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatAdapter(val listener: (ChatItem) -> Unit) : RecyclerView.Adapter<ChatAdapter.SingleViewHolder>() {

    var items: List<ChatItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdapter.SingleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val convertView = inflater.inflate(R.layout.item_chat_single, parent, false)
        return SingleViewHolder(convertView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder:ChatAdapter.SingleViewHolder, position: Int) {
        holder.bind(items[position],listener)
    }

    inner class SingleViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView) ,LayoutContainer, ItemTouchViewHolder {

        override val containerView: View? get() = itemView

        val iv_avatar = convertView.findViewById<ImageView>(R.id.iv_avatar_single)
        val tv_title = convertView.findViewById<TextView>(R.id.tv_title_single)
        val sv_indicator = convertView.findViewById<View>(R.id.sv_indicator)

        fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
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

            itemView.setOnClickListener {
                listener.invoke(item)
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)//To change body of created functions use File | Settings | File Templates.
        }
    }

    fun updateData(data: List<ChatItem>){
        val diffCallback = object : DiffUtil.Callback(){
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].id == data[newPos].id
            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean = items[oldPos].hashCode() == data[newPos].hashCode()
            override fun getOldListSize(): Int = items.size
            override fun getNewListSize(): Int = data.size
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = data
        diffResult.dispatchUpdatesTo(this)
    }

}