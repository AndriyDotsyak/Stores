package com.stores.data.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Store(
    @PrimaryKey
    var id: String = "",
    var name: String? = null,
    var storages: RealmList<Storage>? = null
) : RealmObject()