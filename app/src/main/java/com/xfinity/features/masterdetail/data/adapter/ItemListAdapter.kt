package com.xfinity.features.masterdetail.data.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.xfinity.R
import com.xfinity.data.model.response.RelatedTopic
import com.squareup.picasso.Picasso

import java.util.ArrayList

class ItemListAdapter(context: Context, private val items: MutableList<RelatedTopic>?, private val itemLayout: Int, private var iconVisibility: Boolean) : RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>() {

    private val context: Context
    private var isSearching: Boolean = false
    private var onClick: OnItemClicked? = null
    private val filteredItems: MutableList<RelatedTopic>

    init {
        filteredItems = ArrayList()
        this.context = context.applicationContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return ItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item: RelatedTopic?

        if (isSearching) {
            item = filteredItems[position]
        } else {
            item = items!![position]
        }

        if (iconVisibility) {
            holder.text.visibility = View.GONE
            holder.imageView.visibility = View.VISIBLE
        } else {
            holder.text.visibility = View.VISIBLE
            holder.imageView.visibility = View.GONE
        }

        if (item.icon != null && item.icon!!.url != null && item.icon!!.url != "") {
            Picasso.with(context).load(item.icon!!.url).placeholder(R.drawable.ic_launcher).into(holder.imageView)
        } else {
            Picasso.with(context).load(R.drawable.ic_launcher).into(holder.imageView)
        }

        if (item.text != null) {
            holder.text.text = item.text
            //ITEM CLICK
            holder.itemView.setOnClickListener { v -> onClick!!.onItemClick(items!![position]) }
        }

    }

    fun filter(query: CharSequence) {
        if (!filteredItems.isEmpty()) {
            filteredItems.clear()
        }
        if (items != null && !items.isEmpty()) {
            isSearching = true
            for (result in items) {
                if (result.text!!.contains(query.toString())) {
                    filteredItems.add(result)
                }
            }
        } else {
            isSearching = false
        }
        notifyDataSetChanged()
    }

    fun closeSearch() {
        isSearching = false
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (isSearching) {
            filteredItems.size
        } else {
            items!!.size
        }
    }

    fun addAll(itemsR: List<RelatedTopic>) {
        items!!.clear()
        this.items += itemsR
        notifyDataSetChanged()
    }

    class ItemViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: TextView = itemView.findViewById(R.id.textview_description)
        internal var imageView: ImageView = itemView.findViewById(R.id.imageview_item)
    }

    interface OnItemClicked {
        fun onItemClick(relatedTopic: RelatedTopic)
    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }

    fun setIconVisibility(visibility: Boolean) {
        iconVisibility = visibility
        this.notifyDataSetChanged()
    }

}