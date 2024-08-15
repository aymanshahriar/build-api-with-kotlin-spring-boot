package tv.codealong.tutorials.springboot.thenewboston.service

import org.springframework.stereotype.Service
import tv.codealong.tutorials.springboot.thenewboston.datasource.SatelliteDataSource
import tv.codealong.tutorials.springboot.thenewboston.model.Satellite

@Service
class SatelliteService(private val satelliteDataSource: SatelliteDataSource) {

    fun getAllSatellites(): Collection<Satellite> {
        return satelliteDataSource.findAll()       // Returns list of satellite objects
    }

    fun insertSatellite(satellite: Satellite): Satellite {
        // Insert satellite into database
        return satelliteDataSource.save(satellite)    // this will return the satellite that was saved
    }

    fun updateSatellite(satellite: Satellite, satelliteId: Int): Satellite {
        // Basically, if the user specifies an id in the request body, it will be ignored
        val newSatellite : Satellite = satelliteDataSource.getReferenceById(satelliteId)
        newSatellite.name = satellite.name
        return satelliteDataSource.save(newSatellite)   // The save method updates the satellite, and returns the updated satellite
    }

    fun deleteSatellite(satelliteId: Int): String {
        satelliteDataSource.deleteById(satelliteId)   // method is void type, doesn't delete anything
        return "Deleted";
    }
}