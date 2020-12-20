package ru.maxim.clicker.repo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import ru.maxim.clicker.repo.model.CountEntity;
import ru.maxim.clicker.repo.repository.HibernateCountRepository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CountRepositoryTest {
    @Autowired private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private EntityManager entityManager;
    @Autowired private HibernateCountRepository repository;

    private final CounterRepository counterRepository = new CounterRepository(repository);

    @Test
    public void successGetCount() {
        when(repository.getCounter()).thenReturn(new CountEntity(1));
        when(repository.createCounter()).thenReturn(new CountEntity(1));
        CountEntity result = counterRepository.getCount();
        assertThat(result.getCount()).isEqualTo(1);
    }

    @Test
    public void failGetCount() {
        when(repository.getCounter()).thenReturn(new CountEntity(null));
        CountEntity result = counterRepository.getCount();
        assertThat(result.getCount()).isEqualTo(1);
    }
}
