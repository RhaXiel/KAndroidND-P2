package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.getTodayFormatedDate
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.AsteroidApiFilter
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidRepository(database)

    private val _status = MutableLiveData<AsteroidApiStatus>()

    val status: LiveData<AsteroidApiStatus>
        get() = _status

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

//    private var _asteroids = MutableLiveData<List<Asteroid>>()
//
//    val asteroids : LiveData<List<Asteroid>>
//        get() = _asteroids

    init {
        _status.value = AsteroidApiStatus.DONE
        viewModelScope.launch {
            try {
                _pictureOfDay.value = asteroidsRepository.getPictureOfDay()
            } catch (e: Exception) {
                _pictureOfDay.value = null
            }
            try {
                asteroidsRepository.refreshAsteroids()

            } catch (e: Exception) {
                if (asteroidsRepository.asteroids.value.isNullOrEmpty()) {
                    _status.value = AsteroidApiStatus.ERROR
                }
            }
        }
    }

    val asteroids = asteroidsRepository.asteroids

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    fun updateFilter(filter: AsteroidApiFilter) {
//        viewModelScope.launch {
//            _asteroids.value = when (filter) {
//                AsteroidApiFilter.SHOW_TODAY ->
//                     Transformations.map(asteroidsRepository.asteroids) { records -> records.filter {  it.closeApproachDate == getTodayFormatedDate()} }.value
//                AsteroidApiFilter.SHOW_WEEK -> {
//                    Transformations.map(asteroidsRepository.asteroids) { records -> records.filter { date -> getNextSevenDaysFormattedDates().any { date.closeApproachDate == it}  } }.value
//                }
//                else -> {
//                    asteroidsRepository.asteroids.value
//                }
//            }
//
//        }
    }
}
