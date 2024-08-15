package com.example.containerized_project.controller

import com.example.containerized_project.model.Satellite
import com.example.containerized_project.service.SatelliteService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/satellite")
class SatelliteController(private val satelliteService: SatelliteService) {

    @GetMapping
    fun getAllSatellites(): Collection<Satellite> {
        return satelliteService.getAllSatellites()
    }

    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)  This is commentedout since we're specifying the httpstatus in the ResponseEntity
    // The @Valid annotation makes sure that the request body matches the structure of the satellite object
    fun addSatellite(@Valid @RequestBody satellite: Satellite): ResponseEntity<Satellite> {  // In the http request to this endpoint, we expect the body of this request to contain the details of the satellite
        val addedSatellite = satelliteService.insertSatellite(satellite);
        return ResponseEntity(addedSatellite, HttpStatus.CREATED)   // Having a ResponseEntity is unnecessary, we can just juse the @ResponseStatus annotation
    }

    @PutMapping("/{satelliteId}")
    fun updateSatellite(@PathVariable satelliteId: Int, @Valid @RequestBody satellite: Satellite): ResponseEntity<Satellite> {
        val updatedSatellite = satelliteService.updateSatellite(satellite, satelliteId);
        if (updatedSatellite == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(updatedSatellite, HttpStatus.OK)
    }

    @DeleteMapping("/{satelliteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)          // It seems that this header prevents the "Deleted" text from being returned in the response body
    fun deleteSatellite(@PathVariable satelliteId: Int): ResponseEntity<Satellite> {
        val satelliteDeleted = satelliteService.deleteSatellite(satelliteId);
        if (satelliteDeleted) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

}