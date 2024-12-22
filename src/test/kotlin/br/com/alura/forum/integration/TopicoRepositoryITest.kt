package br.com.alura.forum.integration

import br.com.alura.forum.dto.TopicoReport
import br.com.alura.forum.model.StatusTopico
import br.com.alura.forum.repository.TopicoRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.LocalDateTime

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TopicoRepositoryITest {

    @Autowired
    private lateinit var topicoRepository: TopicoRepository

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
    }


    private val categoria = "Backend"
    private val quantidade = 1L
    private val nomeCurso = "Kotlin"

    @Test
    fun `deve retornar um relatorio com sucesso`() {
        val result = topicoRepository.report()

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
        assertThat(result.first()).isExactlyInstanceOf(TopicoReport::class.java)
        assertThat(result.first().categoria).isEqualTo(categoria)
        assertThat(result.first().quantidade).isEqualTo(quantidade)
    }

    @Test
    fun `deve listar topico pelo nome do curso com sucesso`() {
        val result = topicoRepository.findByCursoNomeContainingIgnoreCase(nomeCurso, PageRequest.of(0,5))

        assertThat(result).isNotNull
        assertThat(result).isNotEmpty
        assertThat(result).isExactlyInstanceOf(PageImpl::class.java)
        assertThat(result.first().id).isEqualTo(1L)
        assertThat(result.first().titulo).isEqualTo("Duvida sobre kotlin")
        assertThat(result.first().mensagem).isEqualTo("Minha funcao let nao funciona")
        assertThat(result.first().status).isEqualTo(StatusTopico.NAO_RESPONDIDO)
        assertThat(result.first().dataCriacao).isBefore(LocalDateTime.now())
        assertThat(result.first().dataAlteracao).isNull()
        assertThat(result.first().curso.id).isEqualTo(1L)
        assertThat(result.first().curso.nome).isEqualTo(nomeCurso)
        assertThat(result.first().curso.categoria).isEqualTo(categoria)
    }

}