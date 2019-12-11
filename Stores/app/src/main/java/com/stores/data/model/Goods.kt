package com.stores.data.model

import io.realm.RealmObject

open class Goods(
    var id: String = "",
    var name: String? = null
) : RealmObject()