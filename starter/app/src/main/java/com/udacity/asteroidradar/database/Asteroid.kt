//package com.udacity.asteroidradar.database
//
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity(tableName = "asteroid_cache_table")
//data class Asteroid(
//    @PrimaryKey(autoGenerate = false)
//    var id: String,
//
//    @ColumnInfo(name = "name")
//    val name : String,
//
//    @ColumnInfo(name = "close_approach_date")
//    val closeApproachDate : String,
//
//    @ColumnInfo(name = "kilometers_per_second")
//    val relativeVelocity : String,
//
//    @ColumnInfo(name = "astronomical")
//    val missDistance: String,
//
//    @ColumnInfo(name = "absolute_magnitude_h")
//    val absoluteMagnitude: Double,
//
//    @ColumnInfo(name = "Estimated_diameter_max")
//    val estimatedDiameter: Double,
//
//    @ColumnInfo(name = "is_potentially_hazardous_asteroid")
//    val isPotentiallyHazardousAsteroid: Boolean
//
//    /*
//    //All measured in KM
//    id
//    name
//    close_approach_data
//        close_approach_date
//        relative_velocity
//            kilometers_per_second
//        miss_distance
//            astronomical
//    absolute_magnitude_h
//    estimated_diameter_km
//        kilometers
//            estimated_diameter_min
//            estimated_diameter_max
//    is_potentially_hazardous_asteroid
//    //image
//    */
//)