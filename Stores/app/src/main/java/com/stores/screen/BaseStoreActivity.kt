package com.stores.screen

import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout
import com.stores.data.model.Default
import com.stores.extensions.isValidInputStore
import com.stores.screen.adapter.StoreAdapter
import com.stores.screen.base.BaseActivity

abstract class BaseStoreActivity : BaseActivity() {

    lateinit var storeAdapter: StoreAdapter
    lateinit var itemSelected: Default

    fun onClickAddItem(buttonAdd: Button, containerAdd: ConstraintLayout) {
        buttonAdd.setOnClickListener {
            buttonAdd.visibility = View.GONE
            containerAdd.visibility = View.VISIBLE
        }
    }

    fun onClickEdit(buttonEdit: Button, containerEdit: ConstraintLayout, containerAdd: ConstraintLayout) {
        buttonEdit.setOnClickListener {
            containerEdit.visibility = View.GONE
            containerAdd.visibility = View.VISIBLE
        }
    }

    fun onClickRemove(buttonRemove: Button, buttonAdd: Button, containerEdit: ConstraintLayout) {
        buttonRemove.setOnClickListener {
            removeItemDB(itemSelected)
            storeAdapter.removeItem(itemSelected)
            containerEdit.visibility = View.GONE
            buttonAdd.visibility = View.VISIBLE
        }
    }

    fun onClickOK(buttonOk: Button, buttonAdd: Button, editTextName: EditText, containerAdd: ConstraintLayout) {
        buttonOk.setOnClickListener {
            val itemName = editTextName.text.toString()

            if (itemName.isValidInputStore()) {
                if (this::itemSelected.isInitialized && itemSelected.isSelected) {
                    editItemOK(itemName)
                } else {
                    addItemOK(itemName)
                }
            }

            containerAdd.visibility = View.GONE
            buttonAdd.visibility = View.VISIBLE

            editTextName.text.clear()
            hideKeyboard(it)
        }
    }

    private fun editItemOK(itemName: String) {
        itemSelected.isSelected = !itemSelected.isSelected
        itemSelected.name = itemName

        editItemDB(itemSelected)
        storeAdapter.editItem(itemSelected)
    }

    private fun addItemOK(itemName: String) {
        addItemDB(itemName)

        storeAdapter.addItem(
            getItemsDB().find {
                    item -> item.name == itemName
            } ?: Default(name =  itemName)
        )
    }

    fun onLongClick(item: Default, buttonAdd: Button, editTextName: EditText,
                    containerAdd: ConstraintLayout, containerEdit: ConstraintLayout) {

        itemSelected = item
        itemSelected.isSelected = !item.isSelected

        if (itemSelected.isSelected) {
            editTextName.setText(itemSelected.name)
            buttonAdd.visibility = View.GONE
            containerAdd.visibility = View.GONE
            containerEdit.visibility = View.VISIBLE
        } else {
            containerAdd.visibility = View.GONE
            containerEdit.visibility = View.GONE
            buttonAdd.visibility = View.VISIBLE
        }

        storeAdapter.editItem(itemSelected)
        storeAdapter.deselectedLast(itemSelected)
    }

    abstract fun addItemDB(name: String)
    abstract fun editItemDB(item: Default)
    abstract fun removeItemDB(item: Default)
    abstract fun getItemsDB(): List<Default>
}