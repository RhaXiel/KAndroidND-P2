package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)

    @Query("SELECT * FROM asteroid_cache_table")
    fun getAllAsteroids(): LiveData<List<DatabaseAsteroid>>

//    @Query("SELECT * FROM asteroid_cache_table ORDER BY id DESC Limit 1")
//    suspend fun getLatest(): Asteroid?

}

