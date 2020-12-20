package ru.maxim.clicker.repo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.maxim.clicker.repo.model.CountEntity;

public interface HibernateCountRepository extends CrudRepository<CountEntity, Integer> {
    @Query(nativeQuery = true, value = "" +
            "UPDATE counter c " +
            "SET count = c.count + 1 " +
            "WHERE c.count = (select count) " +
            "RETURNING c.count;")
    CountEntity updateCounter();

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM counter ORDER BY counter DESC LIMIT 1;")
    CountEntity getCounter();

    @Query(nativeQuery = true, value = "" +
            "INSERT INTO counter(count) " +
            "VALUES (0) " +
            "ON CONFLICT DO NOTHING " +
            "RETURNING count")
    CountEntity createCounter();
}
