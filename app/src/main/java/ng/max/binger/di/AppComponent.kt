package ng.max.binger.di

import android.app.Application
import com.tjohnn.popularmovieskotlin.di.ViewModelBindingModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import ng.max.binger.App
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ActivityBindingModule::class, AndroidSupportInjectionModule::class,
    NetworkModule::class, ViewModelBindingModule::class, RoomModule::class])
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // override fun inject(app: App)
}