package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.network.AsteroidApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> =
            Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()){
                it.asDomainModel()
            }

    suspend fun getPictureOfDay(): PictureOfDay {
        return AsteroidApi.retrofitService.getPictureOfTheDay(Constants.API_KEY)
    }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val dates = getNextSevenDaysFormattedDates()
            val result = AsteroidApi.retrofitService.getAsteroids(dates.first(), dates.last(), Constants.API_KEY).string()
            var obj = JSONObject(result)
            val fetchedAsteroids = parseAsteroidsJsonResult(obj)
            database.asteroidDatabaseDao.insertAll(*fetchedAsteroids.asDatabaseModel())
        }
    }

}