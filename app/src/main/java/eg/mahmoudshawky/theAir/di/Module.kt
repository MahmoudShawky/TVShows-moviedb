package eg.mahmoudshawky.theAir.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import eg.mahmoudshawky.theAir.BuildConfig
import eg.mahmoudshawky.theAir.data.Repository
import eg.mahmoudshawky.theAir.data.RepositoryImp
import eg.mahmoudshawky.theAir.data.local.db.DatabaseHelper
import eg.mahmoudshawky.theAir.data.local.db.DatabaseHelperImp
import eg.mahmoudshawky.theAir.data.local.db.TheAirDatabase
import eg.mahmoudshawky.theAir.data.local.prefs.PreferencesHelper
import eg.mahmoudshawky.theAir.data.local.prefs.PreferencesHelperImp
import eg.mahmoudshawky.theAir.data.models.entity.TvShow
import eg.mahmoudshawky.theAir.data.remote.NetworkConnectionInterceptor
import eg.mahmoudshawky.theAir.data.remote.RemoteRepository
import eg.mahmoudshawky.theAir.data.remote.RemoteRepositoryImp
import eg.mahmoudshawky.theAir.data.remote.ServiceApi
import eg.mahmoudshawky.theAir.ui.details.TvShowDetailsViewModel
import eg.mahmoudshawky.theAir.ui.latestTvShows.LatestTvShowsViewModel
import eg.mahmoudshawky.theAir.utils.FetchType
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel {(tvShowId: Int?) -> LatestTvShowsViewModel(get(), tvShowId) }
    viewModel { (tvShow: TvShow) -> TvShowDetailsViewModel(get(), tvShow) }
}

val repositoryModule = module {
    single<RemoteRepository> { RemoteRepositoryImp(get()) }
    single<PreferencesHelper> { PreferencesHelperImp(get()) }
    single<DatabaseHelper> { DatabaseHelperImp(get()) }
    single<Repository> {
        RepositoryImp(
            remoteRepository = get(),
            databaseHelper = get(),
            preferencesHelper = get()
        )
    }
}

val apiModule = module {
    fun provideApiModule(retrofit: Retrofit): ServiceApi {
        return retrofit.create(ServiceApi::class.java)
    }
    single { provideApiModule(get()) }
}

val retrofitModule = module {

    fun provideCacheFile(context: Context): File {
        return File(context.cacheDir, "okHttp_cache")
    }

    fun provideCache(cacheFile: File): Cache {
        return Cache(cacheFile, 3 * 1024 * 1024)
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    fun provideNetworkConnInterceptor(context: Context): NetworkConnectionInterceptor {
        return NetworkConnectionInterceptor(context)
    }

    fun provideHttpClient(
        cache: Cache,
        loggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: NetworkConnectionInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(networkInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor)
        }
        return builder.build()
    }

    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setLenient().create()
                )
            )
            .client(client)
            .build()
    }

    single { provideCacheFile(androidContext()) }
    single { provideCache(get()) }
    single { provideLoggingInterceptor() }
    single { provideNetworkConnInterceptor(androidContext()) }

    single {
        provideHttpClient(
            cache = get(),
            loggingInterceptor = get(),
            networkInterceptor = get()
        )
    }
    single { provideRetrofit(client = get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): TheAirDatabase {
        return Room.databaseBuilder(application, TheAirDatabase::class.java, "the_air_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    /*fun provideAccountsDao(database: TheAirDatabase): AccountDAO {
        return database.accountsTableDao()
    }*/

    single { provideDatabase(androidApplication()) }
    //single { provideAccountsDao(get()) }
}

