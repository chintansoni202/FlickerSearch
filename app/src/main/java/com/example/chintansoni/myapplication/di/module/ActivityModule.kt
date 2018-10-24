package com.example.chintansoni.myapplication.di.module

import com.example.chintansoni.myapplication.view.activity.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity(): SearchActivity
}