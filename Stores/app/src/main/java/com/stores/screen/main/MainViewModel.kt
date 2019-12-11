package com.stores.screen.main

import androidx.lifecycle.ViewModel
import com.stores.data.model.Default
import com.stores.data.model.Store
import io.realm.Realm
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    fun addItemStore(name: String) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.createObject(Store::class.java, UUID.randomUUID().toString())
                    .name = name
            }
        }
    }

    fun removeItemStore(item: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Store::class.java)
                    .equalTo("id", item.id)
                    .findFirst()
                    ?.deleteFromRealm()
            }
        }
    }

    fun editItemStore(item: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Store::class.java)
                    .equalTo("id", item.id)
                    .findFirst()
                    ?.name = item.name
            }
        }
    }

    fun getItemsStore() : List<Default> {
        val storeCopy: MutableList<Default> = mutableListOf()

        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                val store = transaction.where(Store::class.java).findAll()

                for (item in store)
                    storeCopy.add(Default(item.id, item.name ?: ""))
            }
        }

        return storeCopy
    }
}