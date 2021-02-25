package com.sylko.twitchapi.database

import androidx.paging.DataSource
import com.sylko.twitchapi.entities.GameEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface GameDao {
    @Query("SELECT * FROM game ORDER BY viewers DESC")
    fun getGames(): DataSource.Factory<Int, GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGames(games: List<GameEntity>)

    @Query("SELECT COUNT(*) as count FROM game")
    fun getSize(): Single<Int>
}