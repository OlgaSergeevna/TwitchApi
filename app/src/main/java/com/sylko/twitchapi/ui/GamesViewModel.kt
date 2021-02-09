package com.sylko.twitchapi.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.sylko.twitchapi.api.ApiService
import com.sylko.twitchapi.entities.GameEntity
import com.sylko.twitchapi.paging.FeedDataFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GamesViewModel : ViewModel() {

    var gameList: Observable<PagedList<GameEntity>>
    private val compositeDisposable = CompositeDisposable()
    private val pagedSize = 20
    private val sourceFactory: FeedDataFactory

    init {
        sourceFactory = FeedDataFactory(
            compositeDisposable,
            ApiService.getService()
        )

        val config = PagedList.Config.Builder()
            .setPageSize(pagedSize)
            .setInitialLoadSizeHint(pagedSize * 3)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        gameList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
