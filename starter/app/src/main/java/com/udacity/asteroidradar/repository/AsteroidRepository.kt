package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.main.AsteroidApiStatus
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.AsteroidApiFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {
    val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()){
                it.asDomainModel()
            }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            val dates  = getNextSevenDaysFormattedDates()
            val result  = AsteroidApi.retrofitService.getAsteroids(dates.first(), dates.last(), Constants.API_KEY).string()
            var obj = JSONObject(result)
            val fetchedAsteroids = parseAsteroidsJsonResult(obj)
            database.asteroidDatabaseDao.insertAll(*fetchedAsteroids.asDatabaseModel())

        }
    }
}