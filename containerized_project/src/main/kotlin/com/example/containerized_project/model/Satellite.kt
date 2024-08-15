package com.example.containerized_project.model

import jakarta.persistence.*

@Entity
@Table(name = "satellite")
data class Satellite (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,
    val name: String
)