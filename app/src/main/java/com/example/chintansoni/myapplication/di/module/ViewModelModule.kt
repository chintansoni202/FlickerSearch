package com.example.chintansoni.myapplication.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.chintansoni.myapplication.di.mapkey.ViewModelKey
import com.example.chintansoni.myapplication.viewmodel.KotlinViewModelFactory
import com.example.chintansoni.myapplication.viewmodel.SearchActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchActivityViewModel::class)
    abstract fun bindListViewModel(searchActivityViewModel: SearchActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: KotlinViewModelFactory): ViewModelProvider.Factory
}