package org.wildcodeschool.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.wildcodeschool.myblog.model.ArticleAuthor;

@Repository
public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, Long> {
}
