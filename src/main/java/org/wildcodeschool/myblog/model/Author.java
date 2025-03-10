package org.wildcodeschool.myblog.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 50)
    private String firstname;


    @Column(nullable = false, length = 50)
    private String lastname;


    @OneToMany(mappedBy = "author")
    private List<ArticleAuthor> articleAuthors;
}
