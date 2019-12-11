package com.stores.extensions

import android.text.TextUtils

fun String.isValidInputStore() = !TextUtils.isEmpty(this)