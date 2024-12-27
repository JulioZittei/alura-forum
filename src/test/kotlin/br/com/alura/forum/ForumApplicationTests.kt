package br.com.alura.forum

import com.redis.testcontainers.RedisContainer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ForumApplicationTests {

	companion object {
		@Container
		private val mysqlContainer = MySQLContainer<Nothing>("mysql:8.0.28").apply {
			withDatabaseName("testdb")
			withUsername("testuser")
			withPassword("testpassword")
		}

		@Container
		private val redisContainer = RedisContainer("redis:6.2.6")

		@BeforeAll
		@JvmStatic
		fun startContainer() {
			mysqlContainer.start()
			redisContainer.start()
		}

		@AfterAll
		@JvmStatic
		fun stopContainer() {
			mysqlContainer.stop()
			redisContainer.stop()
		}

		@JvmStatic
		@DynamicPropertySource
		fun properties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl)
			registry.add("spring.datasource.username", mysqlContainer::getUsername)
			registry.add("spring.datasource.password", mysqlContainer::getPassword)
			registry.add("spring.redis.host", redisContainer::getHost)
			registry.add("spring.redis.port", redisContainer::getRedisPort)
		}
	}


	@Test
	fun contextLoads() {
	}

}
