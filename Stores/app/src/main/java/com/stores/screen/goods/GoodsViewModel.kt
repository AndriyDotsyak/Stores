package com.stores.screen.goods

import androidx.lifecycle.ViewModel
import com.stores.data.model.Default
import com.stores.data.model.Goods
import com.stores.data.model.Storage
import com.stores.data.model.Store
import io.realm.Realm
import java.util.*
import javax.inject.Inject

class GoodsViewModel @Inject constructor() : ViewModel() {

    fun addItemGoods(name: String, storageID: String) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Storage::class.java).equalTo("id", storageID).findFirst()
                    ?.goods?.add(realm.copyToRealm(Goods(id = UUID.randomUUID().toString(), name = name)))
            }
        }
    }

    fun removeItemGoods(itemGoods: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Goods::class.java)
                    .equalTo("id", itemGoods.id)
                    .findAll()
                    .deleteAllFromRealm()
            }
        }
    }

    fun editItemGoods(itemGoods: Default) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(Goods::class.java)
                    .equalTo("id", itemGoods.id)
                    .findFirst()
                    ?.name = itemGoods.name
            }
        }
    }

    fun getItemsGoods(storageID: String) : List<Default> {
        val goods: MutableList<Default> = mutableListOf()

        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {transaction ->
                val storage = transaction.where(Storage::class.java).equalTo("id", storageID).findFirst()

                storage?.goods?.let {
                    for (item in it)
                        goods.add(Default(item.id, item.name ?: ""))
                }
            }
        }

        return goods
    }
}