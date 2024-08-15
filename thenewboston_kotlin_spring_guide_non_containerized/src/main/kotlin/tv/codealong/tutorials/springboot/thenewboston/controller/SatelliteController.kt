package tv.codealong.tutorials.springboot.thenewboston.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*   // This imports all the annotations (all the stuff with @)
import tv.codealong.tutorials.springboot.thenewboston.model.Satellite
import tv.codealong.tutorials.springboot.thenewboston.service.SatelliteService
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/satellite")
class SatelliteController(private val satelliteService: SatelliteService) {

    @GetMapping
    fun getAllSatellites(): Collection<Satellite> {
        return satelliteService.getAllSatellites()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    // The @Valid annotation makes sure that the request body matches the structure of the satellite object
    fun addSatellite(@Valid @RequestBody satellite: Satellite): Satellite {  // In the http request to this endpoint, we expect the body of this request to contain the details of the satellite
        return satelliteService.insertSatellite(satellite);
    }

    @PutMapping("/{satelliteId}")
    fun updateSatellite(@PathVariable satelliteId: Int, @Valid @RequestBody satellite: Satellite): Satellite {  // The satellite id will come from the path variable, the satellite object will come from the request body
        return satelliteService.updateSatellite(satellite, satelliteId)
    }

    @PatchMapping("/updateSatelliteWithPatch/{satelliteId}")
    fun updateSatelliteWithPatch() {}

    @DeleteMapping("/{satelliteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)          // It seems that this header prevents the "Deleted" text from being returned in the response body
    fun deleteSatellite(@PathVariable satelliteId: Int): String {
        return satelliteService.deleteSatellite(satelliteId)
    }

}