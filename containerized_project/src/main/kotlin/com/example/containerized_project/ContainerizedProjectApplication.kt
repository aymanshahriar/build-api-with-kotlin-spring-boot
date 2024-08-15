package com.example.containerized_project

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContainerizedProjectApplication

fun main(args: Array<String>) {
    runApplication<ContainerizedProjectApplication>(*args)
}
