package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table (name = "news")
@Data
public class News {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "title")
    private String title;

    @Column (name = "text")
    private String text;

//    @CreationTimestamp
    @Column (name = "creation_time")
    private Instant date;

    @JoinColumn (name = "category_id")
    @ManyToOne
    private Category category;

}
