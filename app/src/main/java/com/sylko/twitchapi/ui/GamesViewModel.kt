package com.sylko.twitchapi.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.sylko.twitchapi.api.ApiFactory
import com.sylko.twitchapi.database.AppDatabase
import com.sylko.twitchapi.entities.GameEntity
import com.sylko.twitchapi.pojo.TopGames
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

class GamesViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val games = db.gameDao().getGames()

    init {
        loadData()
    }

    private fun loadData() {

        val offset = 0

        val disposable = ApiFactory.apiService.loadGames(10, offset)
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
            .delaySubscription(10, TimeUnit.MINUTES)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { it ->
                    db.gameDao().insertGames(
                        it
                    )
                },
                {
                    it.message?.let { it1 -> Log.d("ERROR", it1) }
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}