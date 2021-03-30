package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM asteroid_cache_table")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroid_cache_table WHERE closeApproachDate IN (:dates)")
    fun getWeekAsteroids(dates: List<String>): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroid_cache_table WHERE closeApproachDate = :closeApproachDate")
    fun getTodayAsteroids(closeApproachDate: String): LiveData<List<DatabaseAsteroid>>

}