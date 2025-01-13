package org.wildcodeschool.myblog.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.wildcodeschool.myblog.model.Article;

import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTitle(String title);
    List<Article> findByContentContainingIgnoreCase(String keyword);
    List<Article> findByCreatedAtAfter(LocalDateTime createdAt);
    List<Article> findByOrderByCreatedAtDesc(Limit limit);

}
