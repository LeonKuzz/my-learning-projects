package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table (name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Long id;

    @Column (name = "category_title")
    private String title;

    @OneToMany (mappedBy = "category", cascade = CascadeType.ALL)
    private List<News> newsList;
}
