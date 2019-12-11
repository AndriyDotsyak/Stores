package com.stores.screen.main

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.stores.R
import com.stores.data.model.Default
import com.stores.screen.BaseStoreActivity
import com.stores.screen.base.adapter.BaseRecyclerAdapter
import com.stores.screen.adapter.StoreAdapter
import com.stores.screen.storage.StorageActivity
import kotlinx.android.synthetic.main.store_list.*

class MainActivity : BaseStoreActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this@MainActivity, viewModelFactory).get(MainViewModel::class.java)

        setupRecycler()
        onClickAddItem(button_add_item_SL, container_add_item_SL)
        onClickOK(button_ok_SL, button_add_item_SL, edit_text_name_SL, container_add_item_SL)
        onClickEdit(button_edit_SL, container_edit_SL, container_add_item_SL)
        onClickRemove(button_remove_SL, button_add_item_SL, container_edit_SL)

        button_add_item_SL.setText(R.string.main_add_store)
    }



    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        val decoration = DividerItemDecoration(recycler_view_SL.context, LinearLayoutManager.VERTICAL)
        storeAdapter = StoreAdapter(onItemClickListener, onItemLongClickListener)

        recycler_view_SL.layoutManager = layoutManager
        recycler_view_SL.adapter = storeAdapter
        recycler_view_SL.addItemDecoration(decoration)

        storeAdapter.addItems(mainViewModel.getItemsStore())
    }

    private val onItemClickListener = object : BaseRecyclerAdapter.OnItemClickListener<Default> {
        override fun onItemClick(position: Int, item: Default) {
            StorageActivity.start(this@MainActivity, item.id)
        }
    }

    private val onItemLongClickListener = object : BaseRecyclerAdapter.OnItemLongClickListener<Default> {
        override fun onItemLongClick(position: Int, item: Default) {
            onLongClick(item, button_add_item_SL, edit_text_name_SL,
                container_add_item_SL, container_edit_SL)
        }
    }

    override fun addItemDB(name: String) {
        mainViewModel.addItemStore(name)
    }

    override fun editItemDB(item: Default) {
        mainViewModel.editItemStore(item)
    }

    override fun removeItemDB(item: Default) {
        mainViewModel.removeItemStore(item)
    }

    override fun getItemsDB(): List<Default> = mainViewModel.getItemsStore()
}