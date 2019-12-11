package com.stores.screen.storage

import androidx.lifecycle.ViewModel
import com.stores.data.model.Default
import com.stores.data.model.Storage
import com.stores.data.model.Store
import io.realm.Realm
import java.util.*
import javax.inject.Inject

class StorageViewModel @Inject constructor() : ViewModel() {

    fun addItemStorage(name: String, storeID: String) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Store::class.java).equalTo("id", storeID).findFirst()
                    ?.storages?.add(realm.copyToRealm(Storage(id = UUID.randomUUID().toString(), name = name)))
            }
        }
    }

    fun removeItemStorage(itemStorage: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Storage::class.java)
                    .equalTo("id", itemStorage.id)
                    .findAll()
                    .deleteAllFromRealm()
            }
        }
    }

    fun editItemStorage(itemStorage: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Storage::class.java)
                    .equalTo("id", itemStorage.id)
                    .findFirst()
                    ?.name = itemStorage.name
            }
        }
    }

    fun getItemsStorage(storeID: String) : List<Default> {
        val storages: MutableList<Default> = mutableListOf()

        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {transaction ->
                val store = transaction.where(Store::class.java).equalTo("id", storeID).findFirst()

                store?.storages?.let {
                    for (item in it)
                        storages.add(Default(item.id, item.name ?: ""))
                }
            }
        }

        return storages
    }
}