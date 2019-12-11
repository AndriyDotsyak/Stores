package com.stores.screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.stores.R
import com.stores.data.model.Default
import com.stores.screen.base.adapter.BaseRecyclerAdapter
import com.stores.screen.base.adapter.BaseViewHolder

class StoreAdapter(
    onItemClickListener: OnItemClickListener<Default>,
    onItemLongClickListener: OnItemLongClickListener<Default>
) : BaseRecyclerAdapter<Default, StoreViewHolder>(onItemClickListener, onItemLongClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return StoreViewHolder(
            view,
            onItemClickListener,
            onItemLongClickListener
        )
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun editItem(item: Default) {
        items.find { it.id == item.id }?.apply {
            name = item.name
            isSelected = item.isSelected
        }

        notifyDataSetChanged()
    }

    fun deselectedLast(item: Default) {
        items
            .filter { it.isSelected && it.id != item.id }
            .forEach { it.isSelected = false }

        notifyDataSetChanged()
    }
}

class StoreViewHolder(
    itemView: View,
    onItemClickListener: BaseRecyclerAdapter.OnItemClickListener<Default>,
    onItemLongClickListener: BaseRecyclerAdapter.OnItemLongClickListener<Default>
) : BaseViewHolder<Default>(itemView, onItemClickListener, onItemLongClickListener) {

    private val textViewName: TextView = itemView.findViewById(R.id.text_view_name_IS)
    private val container: ConstraintLayout = itemView.findViewById(R.id.container_IS)

    override fun initUI() {
        textViewName.text = item.name
        selectedItem()
    }

    private fun selectedItem() {
        if (item.isSelected) {
            container.setBackgroundResource(R.color.colorSelected)
        } else {
            container.setBackgroundResource(R.color.design_default_color_background)
        }
    }
}