package com.udacity.asteroidradar

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(val id: Long,
                    val codename: String,
                    val closeApproachDate: String,
                    val absoluteMagnitude: Double,
                    val estimatedDiameter: Double,
                    val relativeVelocity: Double,
                    val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable

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