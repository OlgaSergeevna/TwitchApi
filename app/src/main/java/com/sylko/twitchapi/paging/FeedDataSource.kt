package com.sylko.twitchapi.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.sylko.twitchapi.api.ApiService
import com.sylko.twitchapi.entities.GameEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FeedDataSource(
    private val apiService: ApiService,
    private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, GameEntity>() {

    //первоначальная загрузка данных
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GameEntity>) {
        val numberOfItems = params.requestedLoadSize
        createObservable(0, 1, numberOfItems, callback, null)
    }

    //последующий вызов
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GameEntity>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(
        requestedPage: Int,
        adjacentPage: Int,
        requestedLoadSize: Int,
        initialCallback: LoadInitialCallback<Int, GameEntity>?,
        callback: LoadCallback<Int, GameEntity>?
    ) {
        compositeDisposable.add(
            apiService.loadGames(requestedPage * requestedLoadSize)
                .map { it.top }
                .map {
                    listOf(
                        GameEntity(
                            it[0].game.id,
                            it[0].game.name,
                            it[0].game.box?.large,
                            it[0].channels,
                            it[0].viewers
                        )
                    )
                }
                .repeat()
                .retry()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        Log.d("", "Loading page: $requestedPage")
                        initialCallback?.onResult(response, null, adjacentPage)
                        callback?.onResult(response, adjacentPage)
                    },
                    { error ->
                        Log.e("", "error", error)
                    }
                ))
    }
}