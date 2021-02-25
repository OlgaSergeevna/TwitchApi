package com.sylko.twitchapi.paging

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.paging.PagedList
import androidx.paging.toObservable
import androidx.room.Room
import com.sylko.twitchapi.api.ApiService
import com.sylko.twitchapi.database.AppDatabase
import com.sylko.twitchapi.database.GameDao
import com.sylko.twitchapi.entities.GameEntity
import com.sylko.twitchapi.pojo.TopGames
import com.sylko.twitchapi.pojo.TopListOfData
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class GamesBoundaryCallback constructor(
        private val apiService: ApiService,
        private val application: Application,
        private val compositeDisposable: CompositeDisposable
) :
        PagedList.BoundaryCallback<GameEntity?>() {
    private var limit = 10

    override fun onZeroItemsLoaded() {
        fetchGames()
    }

    override fun onItemAtFrontLoaded(itemAtFront: GameEntity) {

    }

    override fun onItemAtEndLoaded(itemAtEnd: GameEntity) {
        fetchGames()
    }

    private fun fetchGames() {
        compositeDisposable.add(getDBSize()
                .subscribe({ offset ->
                    saveGames(offset)
                    Log.e("", "saveGames")
                }, { error ->
                    Log.e("", "errorDB", error)
                }))
    }

    @SuppressLint("CheckResult")
    private fun saveGames(offset: Int){
        apiService.loadGames(limit, offset)
                .concatMap { Observable.fromIterable(it.top) }
                .map {
                    listOf(GameEntity(
                            it.game.id,
                            it.game.name,
                            it.game.box?.large,
                            it.channels,
                            it.viewers
                    ))
                }
                .subscribeOn(Schedulers.io())
                .subscribe(
                        { response ->
                            AppDatabase.getInstance(application).gameDao().insertGames(response)
                        },
                        { error ->
                            Log.e("", "error Network", error)
                        }
                )

    }

    private fun getDBSize(): Single<Int> {

        return AppDatabase.getInstance(application).gameDao().getSize()
                .subscribeOn(Schedulers.io())
    }

}




