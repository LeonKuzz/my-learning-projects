package org.example.repositories;

import org.example.entities.Category;
import org.example.entities.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {
    List<News> findByCategory(Category category);
}
