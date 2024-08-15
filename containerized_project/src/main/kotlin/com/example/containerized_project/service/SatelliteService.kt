package com.example.containerized_project.service

import com.example.containerized_project.datasource.SatelliteDataSource
import com.example.containerized_project.model.Satellite
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class SatelliteService(private val satelliteDataSource: SatelliteDataSource) {

    fun getAllSatellites(): Collection<Satellite> {
        return satelliteDataSource.findAll().toList()
    }

    fun insertSatellite(satellite: Satellite): Satellite {
        // Insert satellite into database
        return satelliteDataSource.save(satellite)    // this will return the satellite that was saved
    }

    fun updateSatellite(satellite: Satellite, satelliteId: Int): Satellite? {
        // Basically, if the user specifies an id in the request body, it will be ignored
        val existingSatellite : Satellite? = satelliteDataSource.findByIdOrNull(satelliteId)
        if (existingSatellite == null) {
            return null
        }

        val updatedSatellite = existingSatellite.copy(name = satellite.name)

        return satelliteDataSource.save(updatedSatellite)   // The save method updates the satellite, and returns the updated satellite

    }

    fun deleteSatellite(satelliteId: Int): Boolean {
        if (!satelliteDataSource.existsById(satelliteId)) {
            return false
        }
        satelliteDataSource.deleteById(satelliteId)
        return true
    }
}