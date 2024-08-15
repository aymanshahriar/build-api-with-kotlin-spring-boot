package tv.codealong.tutorials.springboot.thenewboston.datasource

import org.springframework.data.jpa.repository.JpaRepository   // In the italian guy's video, CrudRepository is imported instead of JpaRepository
import org.springframework.stereotype.Repository
import tv.codealong.tutorials.springboot.thenewboston.model.Satellite

// The second param in JpaRepository is the data type of the primary key
@Repository
interface SatelliteDataSource: JpaRepository<Satellite, Int>  {

}