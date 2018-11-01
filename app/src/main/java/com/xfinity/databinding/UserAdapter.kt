package com.xfinity.databinding

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.xfinity.R
import com.xfinity.data.model.response.RelatedTopic
import kotlinx.android.synthetic.main.list_item.view.*

class UserAdapter(val context: Context, val onClick: UserAdapter.OnItemClicked) : RecyclerView.Adapter<UserAdapter.UserHolder>(), BindableAdapter<RelatedTopic> {

    var userIds = emptyList<RelatedTopic>()

    override fun setData(items: List<RelatedTopic>) {
        userIds = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserHolder(inflater.inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount() = userIds.size

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(userIds[position], context)
        holder.itemView.setOnClickListener { v -> onClick.onItemClick(userIds[position]) }
    }

    class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(userId: RelatedTopic, context: Context) {
            itemView.userText.text = userId.text

            if (userId.icon != null && userId.icon!!.url != null && userId.icon!!.url != "") {
                Picasso.with(context).load(userId.icon!!.url).placeholder(R.drawable.ic_launcher).into(itemView.imageview_item)
            } else {
                Picasso.with(context).load(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher)
            }
        }
    }

    interface OnItemClicked {
        fun onItemClick(relatedTopic: RelatedTopic)
    }

}