package br.com.alura.forum.controller

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Role
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var jwtUtil: JWTUtil
    private var token: String? = null

    companion object {
        @Container
        private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.28").apply {
            withDatabaseName("testdb")
            withUsername("testuser")
            withPassword("testpassword")
        }

        @BeforeAll
        @JvmStatic
        fun startContainer() {
            mysqlContainer.start()
        }

        @AfterAll
        @JvmStatic
        fun stopContainer() {
            mysqlContainer.stop()
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
            registry.add("spring.datasource.username", mysqlContainer::getUsername)
            registry.add("spring.datasource.password", mysqlContainer::getPassword)
        }

        private const val RECURSO = "/topicos"
    }

    @BeforeEach
    fun beforeEach() {
        token = generateToken()

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(
                SecurityMockMvcConfigurers.springSecurity()
            ).build()
    }

    @Test
    fun `deve retornar topicos com o codigo 200 com sucesso`() {
        mockMvc.get(RECURSO){
            headers {
                token?.let { setBearerAuth(it) }
            }
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun `deve retornar um topico com o codigo 200 com sucesso quando informado um id de topico que existe`() {
        mockMvc.get("$RECURSO/${1L}"){
            headers {
                token?.let { setBearerAuth(it) }
            }
        }.andExpect {
            status { is2xxSuccessful() }
        }
    }

    @Test
    fun `deve retornar codigo 404 quando informado um id de topico que nao existe`() {
        mockMvc.get("$RECURSO/${2L}"){
            headers {
                token?.let { setBearerAuth(it) }
            }
        }.andExpect {
            status { isEqualTo(404) }
        }
    }

    @Test
    fun `deve retornar codigo 403 quando nao informar um token valido`() {
        mockMvc.get(RECURSO).andExpect {
            status { isEqualTo(403) }
        }
    }

    private fun generateToken(): String? {
        val authorities = mutableListOf(Role(id = 1, nome = "LEITURA", usuarios = listOf()))
        return jwtUtil.generateToken("ana@email.com", authorities)
    }
}