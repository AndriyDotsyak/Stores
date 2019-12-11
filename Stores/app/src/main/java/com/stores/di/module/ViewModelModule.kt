package com.stores.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import com.stores.screen.base.ViewModelFactory
import com.stores.screen.goods.GoodsViewModel
import com.stores.screen.main.MainViewModel
import com.stores.screen.storage.StorageViewModel


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StorageViewModel::class)
    abstract fun bindStorageViewModel(viewModel: StorageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GoodsViewModel::class)
    abstract fun bindGoodsViewModel(viewModel: GoodsViewModel): ViewModel
}