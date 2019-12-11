package com.stores.data.model

import io.realm.RealmList
import io.realm.RealmObject

open class Storage (
    var id: String = "",
    var name: String? = null,
    var goods: RealmList<Goods>? = null
) : RealmObject()