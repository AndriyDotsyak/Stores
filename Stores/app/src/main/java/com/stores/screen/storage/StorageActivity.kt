package com.stores.screen.storage

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
import com.stores.screen.goods.GoodsActivity
import kotlinx.android.synthetic.main.store_list.*

class StorageActivity : BaseStoreActivity() {

    companion object {
        const val CURRENT_STORE_ID_KEY = "CURRENT_STORE_ID_KEY"

        fun start(context: Context, storeID: String) {
            val intent = Intent(context, StorageActivity::class.java)
            intent.putExtra(CURRENT_STORE_ID_KEY, storeID)
            context.startActivity(intent)
        }
    }

    private val storeID: String by lazy { intent.extras?.getString(CURRENT_STORE_ID_KEY) ?: "" }
    lateinit var storageViewModel: StorageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        setTitle(R.string.storage_toolbar_title)

        storageViewModel = ViewModelProviders.of(this@StorageActivity, viewModelFactory).get(StorageViewModel::class.java)

        setupRecycler()
        onClickAddItem(button_add_item_SL, container_add_item_SL)
        onClickOK(button_ok_SL, button_add_item_SL, edit_text_name_SL, container_add_item_SL)
        onClickEdit(button_edit_SL, container_edit_SL, container_add_item_SL)
        onClickRemove(button_remove_SL, button_add_item_SL, container_edit_SL)

        button_add_item_SL.setText(R.string.storage_add_storage)
        edit_text_name_SL.setHint(R.string.storage_enter_storage_name)
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        val decoration = DividerItemDecoration(recycler_view_SL.context, LinearLayoutManager.VERTICAL)
        storeAdapter = StoreAdapter(onItemClickListener, onItemLongClickListener)

        recycler_view_SL.layoutManager = layoutManager
        recycler_view_SL.adapter = storeAdapter
        recycler_view_SL.addItemDecoration(decoration)

        storeAdapter.addItems(storageViewModel.getItemsStorage(storeID))
    }

    private val onItemClickListener = object : BaseRecyclerAdapter.OnItemClickListener<Default> {
        override fun onItemClick(position: Int, item: Default) {
            GoodsActivity.start(this@StorageActivity, item.id)
        }
    }

    private val onItemLongClickListener = object : BaseRecyclerAdapter.OnItemLongClickListener<Default> {
        override fun onItemLongClick(position: Int, item: Default) {
            onLongClick(item, button_add_item_SL, edit_text_name_SL,
                container_add_item_SL, container_edit_SL)
        }
    }

    override fun addItemDB(name: String) {
        storageViewModel.addItemStorage(name, storeID)
    }

    override fun editItemDB(item: Default) {
        storageViewModel.editItemStorage(item)
    }

    override fun removeItemDB(item: Default) {
        storageViewModel.removeItemStorage(item)
    }

    override fun getItemsDB(): List<Default> = storageViewModel.getItemsStorage(storeID)
}