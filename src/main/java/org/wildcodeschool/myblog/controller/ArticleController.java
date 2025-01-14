package org.wildcodeschool.myblog.controller;

import org.springframework.data.domain.Limit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.Category;
import org.wildcodeschool.myblog.repository.ArticleRepository;
import org.wildcodeschool.myblog.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {


    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;

    public ArticleController(ArticleRepository articleRepository, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOS = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(convertToDTO(article));
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody Article article) {
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());
        if (article.getCategory() != null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().build();
            }
            article.setCategory(category);
        }
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedArticle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody Article articleDetails) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        if (articleDetails.getCategory() != null) {
            Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElse(null);
            if (category == null) {
                return ResponseEntity.badRequest().build();
            }
            article.setCategory(category);
        }

        Article updatedArticle = articleRepository.save(article);
        return ResponseEntity.ok(convertToDTO(updatedArticle));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) {
            return ResponseEntity.notFound().build();
        }
        articleRepository.delete(article);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search-title")
    public ResponseEntity<List<ArticleDTO>> getArticlesByTitle(@RequestParam String searchTerms){
        List<Article> articles = articleRepository.findByTitle(searchTerms);
        if (articles.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOS = articles.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOS);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleDTO>> getArticlesByContent(@RequestParam String keyword){
        List<Article> articlesByContent = articleRepository.findByContentContainingIgnoreCase(keyword);
        if (articlesByContent.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOS = articlesByContent.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOS);
    }

    @GetMapping("/created-after")
    public ResponseEntity<List<ArticleDTO>> getArticlesByCreatedAfter(@RequestParam LocalDateTime createdAfter){
        List<Article> articlesByCreatedAfter = articleRepository.findByCreatedAtAfter(createdAfter);
        if (articlesByCreatedAfter.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ArticleDTO> articleDTOS = articlesByCreatedAfter.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOS);
    }

    @GetMapping("/order")
    public ResponseEntity<List<Article>> getFiveLastArticles(){
        List<Article> fiveLastArticles = articleRepository.findByOrderByCreatedAtDesc(Limit.of(5));
        if (fiveLastArticles.isEmpty()) {
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(fiveLastArticles);
    }

    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }
        return articleDTO;
    }

}
