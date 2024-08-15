package tv.codealong.tutorials.springboot.thenewboston.model
import javax.persistence.*

@Entity
@Table(name = "satellite")            // Allows us to define the characteristics of the sql table
class Satellite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)                   // This enables auto-increment
    var id: Int = 0

    @Column(name = "name", nullable = false)    // name cannot be null
    var name: String = ""


// For columns involving dates/timestamps, rewatch the video https://www.youtube.com/watch?v=gp9EZgCf2LQ&list=PLWBhA495dLiPJVv2sqGCfRui42ys8Upit&index=6
}