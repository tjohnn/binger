package ng.max.binger.di

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import ng.max.binger.activities.MainActivity
import ng.max.binger.fragments.AiringTodayFragment
import ng.max.binger.fragments.PopularShowsFragment

@Module
abstract class MainActivityModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun popularShowsFragment(): PopularShowsFragment

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun airingTodayFragment(): AiringTodayFragment


    @ActivityScoped
    @Binds
    abstract fun activity(mainActivity: MainActivity): AppCompatActivity
}