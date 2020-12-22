package ru.maxim.clicker.repo;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.maxim.clicker.repo.model.CountEntity;
import ru.maxim.clicker.repo.repository.HibernateCountRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {CountRepositoryTest.Initializer.class})
public class CountRepositoryTest {

    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("docker");

    @Value("http://localhost:${local.server.port}")
    String baseUrl;

    @Autowired
    HibernateCountRepository repository;

    @AfterEach
    public void after() {
        repository.deleteAll();
    }

    @Test
    public void createCounter() {
        CountEntity result = repository.createCounter();
        assertThat(result.getCount()).isEqualTo(0);
    }

    @Test
    public void updateCounterBYMultipleUsers() throws InterruptedException {
        int usersNum = 5;
        int clicksByEveryUser = 5;

        Runnable task = () -> {
            for (int i = 0; i < clicksByEveryUser; i++) {
                repository.updateCounter();
            }
        };
        CountEntity createResult = repository.createCounter();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        for (int i = 0; i < usersNum; i++) {
            executor.execute(task);
        }
        executor.awaitTermination(10, TimeUnit.SECONDS);
        CountEntity result = repository.getCounter();
        assertThat(result.getCount()).isEqualTo(createResult.getCount() + clicksByEveryUser * usersNum);
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
