package com.skillbox.fibonacci;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FibonacciRepositoryTest extends PostgresTestContainerInitializer {

    @Autowired
    FibonacciRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("test added new number")
    public void testAddedNewNumber () {
        FibonacciNumber number = new FibonacciNumber(1, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);
        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 1",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );
        assertEquals(number.getValue(), actual.get(0).getValue());
        assertEquals(number.getIndex(), actual.get(0).getIndex());
    }

    @Test
    @DisplayName("test findByIndex")
    public void testFindByIndex () {
        FibonacciNumber number = new FibonacciNumber(1, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);
        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 1",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );
        assertEquals(number.getIndex(), repository.findByIndex(1).get().getIndex());
        assertEquals(number.getValue(), repository.findByIndex(1).get().getValue());
    }

    @Test
    @DisplayName("test checking for duplicates")
    public void testCheckingForDuplicates () {
        FibonacciNumber number = new FibonacciNumber(1, 1);
        repository.save(number);
        entityManager.flush();
        entityManager.detach(number);
        FibonacciNumber number1 = new FibonacciNumber(1, 1);
        repository.save(number1);
        entityManager.flush();
        entityManager.detach(number1);
        List<FibonacciNumber> actual = jdbcTemplate.query(
                "SELECT * FROM fibonacci_number WHERE index = 1",
                (rs, rowNum) -> new FibonacciNumber(rs.getInt("index"), rs.getInt("value"))
        );
        assertEquals(1, actual.size());
    }
}
