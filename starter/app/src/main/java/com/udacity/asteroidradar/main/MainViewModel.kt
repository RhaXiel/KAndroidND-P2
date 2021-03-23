package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.AsteroidApiFilter
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData<AsteroidApiStatus>()

    val status: LiveData<AsteroidApiStatus>
        get() = _status

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()

    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    init {
        getPictureOfDay()
        getAsteroids(AsteroidApiFilter.SHOW_WEEK)
    }

    private fun getPictureOfDay(){
        viewModelScope.launch {
            try {
                val result = AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
                _pictureOfDay.value = result
            } catch (e: Exception){
                _status.value = AsteroidApiStatus.ERROR
                _pictureOfDay.value = null
            }
        }
    }

    private fun getAsteroids(filter: AsteroidApiFilter){
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            try {
                val dates  = getNextSevenDaysFormattedDates()
                var startDate : String = ""
                var endDate : String = ""
                when (filter) {
                    AsteroidApiFilter.SHOW_WEEK -> {
                        startDate = dates.first()
                        endDate = dates.last()
                    }
                    AsteroidApiFilter.SHOW_TODAY -> {
                        startDate = dates.first()
                        endDate = dates.elementAt(1)
                    }
                    AsteroidApiFilter.SHOW_SAVED -> {
                        //Do work from database
                        _status.value = AsteroidApiStatus.DONE
                        _asteroids.value = ArrayList()
                        return@launch
                    }
                    else -> {
                        //AN ERROR OCCURRED
                    }
                }
                val result  = AsteroidApi.retrofitService.getAsteroids(startDate, endDate, API_KEY).string()
                var obj = JSONObject(result)
                _asteroids.value = parseAsteroidsJsonResult(obj)
                _status.value = AsteroidApiStatus.DONE
            } catch (e: Exception){
                _status.value = AsteroidApiStatus.ERROR
                _asteroids.value = ArrayList()
            }
        }
    }

    fun displayAsteroidDetails(asteroid: Asteroid){
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete(){
        _navigateToSelectedAsteroid.value = null
    }

    fun updateFilter(filter: AsteroidApiFilter){
        getAsteroids(filter)
    }
}
