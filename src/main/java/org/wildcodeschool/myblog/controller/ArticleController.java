package org.wildcodeschool.myblog.controller;

import org.springframework.data.domain.Limit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.exception.ResourceNotFoundException;
import org.wildcodeschool.myblog.mapper.ArticleMapper;
import org.wildcodeschool.myblog.model.*;
import org.wildcodeschool.myblog.repository.*;
import org.wildcodeschool.myblog.service.ArticleService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;


    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
                List<ArticleDTO> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        return (article == null) ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(article);
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        ArticleDTO savedArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedArticle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, articleDetails);
        return (updatedArticle != null) ? ResponseEntity.ok(updatedArticle) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search-title")
    public ResponseEntity<List<ArticleDTO>> getArticlesByTitle(@RequestParam String searchTerms){
        List<ArticleDTO> articles = articleService.getArticlesByTitle(searchTerms);
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam String keyword){
        List<ArticleDTO> articlesByContent = articleService.getArticlesByContent(keyword);
        return (articlesByContent.isEmpty()) ? ResponseEntity.noContent().build():
                ResponseEntity.ok(articlesByContent);
    }

    @GetMapping("/created-after")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCreatedAfter(@RequestParam LocalDateTime createdAfter){
        List<ArticleDTO> articlesByCreatedAfter = articleService.getArticlesByCreatedAfter(createdAfter);
       return (articlesByCreatedAfter.isEmpty()) ? ResponseEntity.noContent().build() :
               ResponseEntity.ok(articlesByCreatedAfter);
    }

    @GetMapping("/order")
    public ResponseEntity<List<Article>> getFiveLastArticles(){
        List<Article> fiveLastArticles = articleService.getFiveLastArticles();
        return (fiveLastArticles.isEmpty()) ? (ResponseEntity.noContent().build()) :
                ResponseEntity.ok(fiveLastArticles);
    }

}
