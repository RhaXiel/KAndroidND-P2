//package com.udacity.asteroidradar.database
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//
//@Dao
//interface AsteroidDatabaseDao {
//    @Insert
//    suspend fun insert(asteroid: Asteroid)
//
//    @Update
//    suspend fun update(asteroid: Asteroid)
//
//    @Query("SELECT * FROM asteroid_cache_table WHERE id = :key")
//    suspend fun get(key: String): Asteroid?
//
//    @Query("DELETE FROM asteroid_cache_table")
//    suspend fun clear()
//
//    @Query("SELECT * FROM asteroid_cache_table ORDER BY id DESC")
//    fun getAllAsteroids(): LiveData<List<Asteroid>>
//
//    @Query("SELECT * FROM asteroid_cache_table ORDER BY id DESC Limit 1")
//    suspend fun getLatest(): Asteroid?
//
//    @Query("SELECT * FROM asteroid_cache_table WHERE id = :key")
//    fun getAsteroidById(key: String): LiveData<Asteroid>
//}