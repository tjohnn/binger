package com.tjohnn.popularmovieskotlin.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ng.max.binger.viewmodels.AiringTodayViewModel
import ng.max.binger.viewmodels.DetailsViewModel
import ng.max.binger.viewmodels.FavoritesViewModel
import ng.max.binger.viewmodels.PopularShowsViewModel

@Module
abstract class ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(PopularShowsViewModel::class)
    abstract fun popularShowsViewModel(popularShowsViewModel: PopularShowsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AiringTodayViewModel::class)
    abstract fun airingTodayViewModel(airingTodayViewModel: AiringTodayViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun detailsViewModel(detailsViewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun favoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}