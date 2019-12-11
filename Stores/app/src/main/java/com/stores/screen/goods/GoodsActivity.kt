package com.stores.screen.goods

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stores.R
import com.stores.data.model.Default
import com.stores.screen.BaseStoreActivity
import com.stores.screen.adapter.StoreAdapter
import com.stores.screen.base.adapter.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.store_list.*

class GoodsActivity : BaseStoreActivity() {

    companion object {
        const val CURRENT_STORAGE_ID_KEY = "CURRENT_STORAGE_ID_KEY"

        fun start(context: Context, storageID: String) {
            val intent = Intent(context, GoodsActivity::class.java)
            intent.putExtra(CURRENT_STORAGE_ID_KEY, storageID)
            context.startActivity(intent)
        }
    }

    private val storageID: String by lazy { intent.extras?.getString(CURRENT_STORAGE_ID_KEY) ?: "" }
    lateinit var goodsViewModel: GoodsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods)
        setTitle(R.string.goods_toolbar_title)

        goodsViewModel = ViewModelProviders.of(this@GoodsActivity, viewModelFactory).get(GoodsViewModel::class.java)

        setupRecycler()
        onClickAddItem(button_add_item_SL, container_add_item_SL)
        onClickOK(button_ok_SL, button_add_item_SL, edit_text_name_SL, container_add_item_SL)
        onClickEdit(button_edit_SL, container_edit_SL, container_add_item_SL)
        onClickRemove(button_remove_SL, button_add_item_SL, container_edit_SL)

        button_add_item_SL.setText(R.string.goods_add_goods)
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        val decoration = DividerItemDecoration(recycler_view_SL.context, LinearLayoutManager.VERTICAL)
        storeAdapter = StoreAdapter(onItemClickListener, onItemLongClickListener)

        recycler_view_SL.layoutManager = layoutManager
        recycler_view_SL.adapter = storeAdapter
        recycler_view_SL.addItemDecoration(decoration)

        storeAdapter.addItems(goodsViewModel.getItemsGoods(storageID))
    }

    private val onItemClickListener = object : BaseRecyclerAdapter.OnItemClickListener<Default> {
        override fun onItemClick(position: Int, item: Default) {
            // TODO View Goods
        }
    }

    private val onItemLongClickListener = object : BaseRecyclerAdapter.OnItemLongClickListener<Default> {
        override fun onItemLongClick(position: Int, item: Default) {
            onLongClick(item, button_add_item_SL, edit_text_name_SL,
                container_add_item_SL, container_edit_SL)
        }
    }

    override fun addItemDB(name: String) {
        goodsViewModel.addItemGoods(name, storageID)
    }

    override fun editItemDB(item: Default) {
        goodsViewModel.editItemGoods(item)
    }

    override fun removeItemDB(item: Default) {
        goodsViewModel.removeItemGoods(item)
    }

    override fun getItemsDB(): List<Default> = goodsViewModel.getItemsGoods(storageID)
}