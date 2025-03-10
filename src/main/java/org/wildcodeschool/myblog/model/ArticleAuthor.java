package org.wildcodeschool.myblog.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ArticleAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @Column(nullable = false, length = 50)
    private String contribution;

}
