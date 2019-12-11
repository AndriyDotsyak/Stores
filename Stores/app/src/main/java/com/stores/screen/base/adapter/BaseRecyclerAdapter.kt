package com.stores.screen.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<I: Any, VH: BaseViewHolder<I>>(
    val onItemClickListener: OnItemClickListener<I>,
    val onItemLongClickListener: OnItemLongClickListener<I>
) : RecyclerView.Adapter<VH>() {

    val items = mutableListOf<I>()

    interface OnItemClickListener<I> {
        fun onItemClick(position: Int, item: I)
    }

    interface OnItemLongClickListener<I> {
        fun onItemLongClick(position: Int, item: I)
    }

    override fun getItemCount() = items.size

    fun addItems(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: I) {
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    fun removeItem(item: I) {
        items.remove(item)
        notifyDataSetChanged()
    }

    abstract fun editItem(item: I)
}

abstract class BaseViewHolder<T: Any>(
    itemView: View,
    onItemClickListener: BaseRecyclerAdapter.OnItemClickListener<T>,
    onItemLongClickListener: BaseRecyclerAdapter.OnItemLongClickListener<T>
) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: T

    init {
        itemView.setOnClickListener {
            onItemClickListener.onItemClick(adapterPosition, item)
        }

        itemView.setOnLongClickListener {
            onItemLongClickListener.onItemLongClick(adapterPosition, item)
            true
        }
    }

    fun onBind(item: T) {
        this.item = item
        initUI()
    }

    abstract fun initUI()
}