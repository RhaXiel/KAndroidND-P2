package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.api.getNextSevenDaysFormattedDates
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.network.AsteroidApi
import com.udacity.asteroidradar.network.AsteroidApiFilter
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

enum class AsteroidApiStatus { LOADING, ERROR, DONE }

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData<AsteroidApiStatus>()

    val status: LiveData<AsteroidApiStatus>
        get() = _status

    private val _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()

    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    init {
        getAsteroids(AsteroidApiFilter.SHOW_WEEK)
    }

    private fun getAsteroids(filter: AsteroidApiFilter){
        viewModelScope.launch {
            _status.value = AsteroidApiStatus.LOADING
            try {
                val dates = getNextSevenDaysFormattedDates()
                val result  = AsteroidApi.retrofitService.getAsteroids(dates.first(), dates.last(),"cLAk0LBCbzKCZ9yrpV1T3UdxLQiFB4kTf0rqx3cv").string()
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
