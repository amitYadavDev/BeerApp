package app.exam.beerapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import app.exam.beerapp.data.local.BeerDatabase
import app.exam.beerapp.data.local.BeerEntity
import app.exam.beerapp.data.remote.BeerApiService
import app.exam.beerapp.data.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesBeerApiService(): BeerApiService {
        return Retrofit.Builder()
            .baseUrl(BeerApiService.BEAR_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun providesBeerDatabase(@ApplicationContext context: Context): BeerDatabase {
        return Room.databaseBuilder(
            context,
            BeerDatabase::class.java,
            "beer_db"
        ).build()
    }


    @Singleton
    @Provides
    fun provideBeerPager(beerDatabase: BeerDatabase, beerApiService: BeerApiService): Pager<Int, BeerEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BeerRemoteMediator(
                beerDatabase,
                beerApiService
            ),
            pagingSourceFactory = {
                beerDatabase.getDao().pagingSource()
            }
        )
    }


}