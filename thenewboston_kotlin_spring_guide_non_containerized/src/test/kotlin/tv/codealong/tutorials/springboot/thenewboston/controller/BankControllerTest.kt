package tv.codealong.tutorials.springboot.thenewboston.controller

import com.fasterxml.jackson.databind.ObjectMapper // the default library that spring uses to convert objects to json
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*
import tv.codealong.tutorials.springboot.thenewboston.model.Bank
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@SpringBootTest
@AutoConfigureMockMvc
class BankControllerTest @Autowired constructor(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {


    @Nested // tells junit to look at all test cases in this class
    @DisplayName("GET /api/banks")  // You can change this to be anything, it doesn't matter
    @TestInstance(Lifecycle.PER_CLASS)  // junit is not supposed to create a new instance of this class for every test case it runs inside this class
    inner class getBanks {
        @Test
        fun `should return all banks`() {
            mockMvc.get("/api/banks")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }
    }

    @Nested // tells junit to look at all test cases in this class
    @DisplayName("GET /api/banks/{accountNumber}")  // You can change this to be anything, it doesn't matter
    @TestInstance(Lifecycle.PER_CLASS)  // junit is not supposed to create a new instance of this class for every test case it runs inside this class
    inner class getBank {
        @Test
        fun `should return the bank with the given account number`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("3.14") }
                    jsonPath("$.transactionFee") { value("17") }
                }
        }

        @Test
        fun `should return NOT FOUND if the account number does not exist`() {
            // given
            val accountNumber = "does_not_exist"

            // when/then
            mockMvc.get("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested // tells junit to look at all test cases in this class
    @DisplayName("POST /api/banks")    // You can change this to be anything, it doesn't matter
    @TestInstance(Lifecycle.PER_CLASS)  // junit is not supposed to create a new instance of this class for every test case it runs inside this class
    inner class PostNewBank {
        @Test
        fun `should add the new bank`() {
            // given
            val newBank = Bank("acc123", 31.415, 2)

            // when
            val performPost = mockMvc.post("/api/banks") {
                // This is the body of our request
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)  // newBank is converted to json
            }

            // then
            performPost
                .andDo { print() }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))  // checks whether the returned json object is equal to newBank
                    }
                }

            mockMvc.get("/api/banks/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) }}   // tests that the newly added bank actually persists
        }

        @Test
        fun `should return BAD REQUEST (400) if bank with given account number already exists`() {
            // given
            val invalidBank = Bank("1234", 1.0, 1)

            // when
            val performPost = mockMvc.post("/api/banks") {
                // This is the body of our request
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)  // writeValueAsString converts things to json
            }

            // then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }

    @Nested // tells junit to look at all test cases in this class
    @DisplayName("PUT /api/banks")    // You can change this to be anything, it doesn't matter
    @TestInstance(Lifecycle.PER_CLASS)  // junit is not supposed to create a new instance of this class for every test case it runs inside this class
    inner class PutNewBank {
        @Test
        fun `should update an existing bank`() {
            // given
            val updatedBank = Bank("1234", 1.0, 1)

            // when
            val performPutRequest = mockMvc.put("/api/banks") {
                // This is the body of our request
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updatedBank)  // writeValueAsString converts things to json
            }
            // then
            performPutRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(updatedBank))  // checks whether the returned json object is equal to updatedBank
                    }
                }

            mockMvc.get("/api/banks/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) }}   // tests that the update actually persists
        }

        @Test
        fun `should return BAD REQUEST (400) if no bank with given account number exists`() {
            // given
            val nonExistingBank = Bank("does_not_exist", 1.0, 1)

            // when
            val performPutRequest = mockMvc.put("/api/banks") {
                // This is the body of our request
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(nonExistingBank)  // writeValueAsString converts things to json
            }

            // then
            performPutRequest
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested // tells junit to look at all test cases in this class
    @DisplayName("DELETE /api/banks/{accountNumber}")    // You can change this to be anything, it doesn't matter
    @TestInstance(Lifecycle.PER_CLASS)  // junit is not supposed to create a new instance of this class for every test case it runs inside this class
    inner class DeleteExistingBank {

        @Test
        fun `should return NOT FOUND if no bank with the given account number exists`() {
            // given
            val accountNumber = "does not exist"

            // when/then
            mockMvc.delete("/api/banks/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        @DirtiesContext  //  Tells the program that once this test case runs, it makes changes that might affect the other test cases inside this inner class. So spring re-initializes the application context
        fun `should delete the bank with the given account number`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMvc.delete("/api/banks/$accountNumber")
                .andExpect {
                    status { isNoContent() }
                }

            mockMvc.get("/api/banks/$accountNumber")
                .andExpect { status { isNotFound() } }
        }
    }


}