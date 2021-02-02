package com.sylko.twitchapi.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.sylko.twitchapi.api.ApiFactory
import com.sylko.twitchapi.database.AppDatabase
import com.sylko.twitchapi.entities.GameEntity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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
            .delaySubscription(10, TimeUnit.MINUTES)
            .repeat()
            .retry()
            .subscribeOn(Schedulers.io())
            .subscribe({ it ->
                db.gameDao().insertGames(it.top.map {
                    GameEntity(
                        it.game.id,
                        it.game.name,
                        it.game.box?.large,
                        it.channels,
                        it.viewers
                    )
                })
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