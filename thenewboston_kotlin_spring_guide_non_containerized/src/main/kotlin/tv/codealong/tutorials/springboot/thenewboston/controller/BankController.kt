package tv.codealong.tutorials.springboot.thenewboston.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tv.codealong.tutorials.springboot.thenewboston.model.Bank
import tv.codealong.tutorials.springboot.thenewboston.service.BankService

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    // If any of the methods below throw a NoSuchElementException, then make that method return a ResponseEntity. Note: the methods below might throw a NoSuchElementException because methods in MockBankDataSource might throw it.
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    // If any of the methods below throw an IllegalArgumentException, then make that method return a ResponseEntity. Note: the methods below might throw an IllegalArgumentException because methods in MockBankDataSource might throw it.
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleBadRequest(e: IllegalArgumentException): ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()

    @GetMapping("/{accountNumber}")
    fun getBank(@PathVariable accountNumber: String): Bank = service.getBank(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)         // makes sure that 201 (created) is returned instead of 200 (ok)
    fun addBank(@RequestBody bank: Bank): Bank = service.addBank(bank)          // RequestBody annotation indicates that the bank variable gets it's value from the request body

    @PutMapping
    fun updateBank(@RequestBody bank: Bank): Bank = service.updateBank(bank)

    @DeleteMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@PathVariable accountNumber: String): Unit = service.deleteBank(accountNumber)    // Unit indicates we are not returning anything in kotlin (similar to void in Java)
}