package com.stores.di.module

import com.stores.screen.goods.GoodsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.stores.screen.main.MainActivity
import com.stores.screen.storage.StorageActivity

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity
}

@Module
abstract class StorageActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeStorageActivity(): StorageActivity
}

@Module
abstract class GoodsActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeGoodsActivity(): GoodsActivity
}