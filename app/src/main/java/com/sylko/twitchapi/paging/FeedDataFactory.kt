package com.sylko.twitchapi.paging

import androidx.paging.DataSource
import com.sylko.twitchapi.api.ApiService
import com.sylko.twitchapi.entities.GameEntity
import io.reactivex.disposables.CompositeDisposable

class FeedDataFactory(
    private val compositeDisposable: CompositeDisposable,
    private val apiService: ApiService
) : DataSource.Factory<Int, GameEntity>() {

    override fun create(): DataSource<Int, GameEntity> {
        return FeedDataSource(apiService, compositeDisposable)
    }
}