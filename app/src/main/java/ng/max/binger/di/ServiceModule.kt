package ng.max.binger.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ng.max.binger.services.SyncService

@Module
abstract class ServiceModule {

    @ContributesAndroidInjector()
    abstract fun syncService(): SyncService

}