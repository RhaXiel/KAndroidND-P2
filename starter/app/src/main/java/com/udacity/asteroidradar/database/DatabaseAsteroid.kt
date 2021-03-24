package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid

@Entity(tableName = "asteroid_cache_table")
data class DatabaseAsteroid(
    @PrimaryKey
    var id: Long,
    val codename : String,
    val closeApproachDate : String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
    /*
    //All measured in KM
    id
    name
    close_approach_data
        close_approach_date
        relative_velocity
            kilometers_per_second
        miss_distance
            astronomical
    absolute_magnitude_h
    estimated_diameter_km
        kilometers
            estimated_diameter_min
            estimated_diameter_max
    is_potentially_hazardous_asteroid
    //image
    */
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid>{
    return map{
        Asteroid(
                id = it.id,
                codename =  it.codename,
                closeApproachDate = it.closeApproachDate,
                absoluteMagnitude = it.absoluteMagnitude,
                estimatedDiameter = it.estimatedDiameter,
                relativeVelocity = it.relativeVelocity,
                distanceFromEarth = it.distanceFromEarth,
                isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

