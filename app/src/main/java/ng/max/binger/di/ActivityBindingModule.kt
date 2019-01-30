package ng.max.binger.di


import dagger.Module
import dagger.android.ContributesAndroidInjector
import ng.max.binger.activities.DetailsActivity
import ng.max.binger.activities.FavoritesActivity
import ng.max.binger.activities.MainActivity


@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    internal abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun detailsActivity(): DetailsActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [])
    internal abstract fun davoritesActivity(): FavoritesActivity
}
