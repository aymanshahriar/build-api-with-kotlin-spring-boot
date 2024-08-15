package com.example.containerized_project.datasource

import com.example.containerized_project.model.Satellite
import org.springframework.data.repository.CrudRepository   // The tech-with-gio turotial imported from JpaRepository instead of CrudRepository!

// The second param in CrudRepository is the data type of the primary key
interface SatelliteDataSource: CrudRepository<Satellite, Int> {

}