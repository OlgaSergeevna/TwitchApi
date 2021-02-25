package com.sylko.twitchapi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.sylko.twitchapi.api.ApiService
import com.sylko.twitchapi.database.AppDatabase
import com.sylko.twitchapi.entities.GameEntity
import com.sylko.twitchapi.paging.GamesBoundaryCallback
import io.reactivex.disposables.CompositeDisposable

class GamesViewModel(application: Application): AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private var gamesBoundaryCallback: GamesBoundaryCallback? = null
    var userList: LiveData<PagedList<GameEntity>>? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        gamesBoundaryCallback = GamesBoundaryCallback(
                ApiService.getService(),
                application,
            compositeDisposable
        )

        val pagedListConfig = PagedList.Config.Builder()
                .setPrefetchDistance(10)
                .setPageSize(10)
                .setInitialLoadSizeHint(30)
                .setEnablePlaceholders(false)
                .build()

        userList = LivePagedListBuilder(
                db.gameDao().getGames(),
                pagedListConfig
        ).setBoundaryCallback(gamesBoundaryCallback)
                .build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
