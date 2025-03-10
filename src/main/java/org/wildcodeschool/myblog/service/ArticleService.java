package org.wildcodeschool.myblog.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.exception.ResourceNotFoundException;
import org.wildcodeschool.myblog.mapper.ArticleMapper;
import org.wildcodeschool.myblog.model.*;
import org.wildcodeschool.myblog.repository.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final AuthorRepository authorRepository;
    private final ArticleAuthorRepository articleAuthorRepository;

    public List<ArticleDTO> getAllArticles() {
        return  articleRepository.findAll()
                .stream()
                .map(articleMapper::toDTO).toList();
    }

    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        return articleMapper.toDTO(article);
    }


    public ArticleDTO createArticle(ArticleCreateDTO articleCreateDTO) {
        Article article = articleMapper.toEntity(articleCreateDTO);
        LocalDateTime now = LocalDateTime.now();
        article.setCreatedAt(now);
        article.setUpdatedAt(now);

        if (article.getCategory() != null) {
            Category category = categoryRepository.findById(article.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("La catégorie avec l'id " + article.getCategory().getId() + " n'a pas été trouvée"));
            article.setCategory(category);
        }

        if (article.getImages() != null && !article.getImages().isEmpty()) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : article.getImages()) {
                if (image.getId() != null) {
                    // Vérification des images existantes
                    Image existingImage = imageRepository.findById(image.getId()).orElse(null);
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return null;
                    }
                } else {
                    // Création de nouvelles images
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            article.setImages(validImages);
        }
        Article savedArticle = articleRepository.save(article);
        if (article.getArticleAuthors() != null) {
            for (ArticleAuthor articleAuthor : article.getArticleAuthors()) {
                Author author = articleAuthor.getAuthor();
                author = authorRepository.findById(author.getId()).orElse(null);
                if (author == null) {
                    return null;
                }

                articleAuthor.setAuthor(author);
                articleAuthor.setArticle(savedArticle);
                articleAuthor.setContribution(articleAuthor.getContribution());

                articleAuthorRepository.save(articleAuthor);
            }
        }
        return articleMapper.toDTO(savedArticle);
    }


    public ArticleDTO updateArticle(Long id,Article articleDetails) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        article.setTitle(articleDetails.getTitle());
        article.setContent(articleDetails.getContent());
        article.setUpdatedAt(LocalDateTime.now());

        if (articleDetails.getCategory() != null) {
            Category category = categoryRepository.findById(articleDetails.getCategory().getId()).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));

            article.setCategory(category);
        }

        if (articleDetails.getImages() != null) {
            List<Image> validImages = new ArrayList<>();
            for (Image image : articleDetails.getImages()) {
                if (image.getId() != null) {
                    // Vérification des images existantes
                    Image existingImage = imageRepository.findById(image.getId()).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
                    if (existingImage != null) {
                        validImages.add(existingImage);
                    } else {
                        return null; // Image non trouvée, retour d'une erreur
                    }
                } else {
                    // Création de nouvelles images
                    Image savedImage = imageRepository.save(image);
                    validImages.add(savedImage);
                }
            }
            // Mettre à jour la liste des images associées
            article.setImages(validImages);
        } else {
            // Si aucune image n'est fournie, on nettoie la liste des images associées
            article.getImages().clear();
        }
        if (articleDetails.getArticleAuthors() != null) {
            articleAuthorRepository.deleteAll(article.getArticleAuthors());

            List<ArticleAuthor> updatedArticleAuthors = new ArrayList<>();

            for (ArticleAuthor articleAuthorDetails : articleDetails.getArticleAuthors()) {
                Author author = articleAuthorDetails.getAuthor();
                author = authorRepository.findById(author.getId()).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));

                ArticleAuthor newArticleAuthor = new ArticleAuthor();
                newArticleAuthor.setAuthor(author);
                newArticleAuthor.setArticle(article);
                newArticleAuthor.setContribution(articleAuthorDetails.getContribution());

                updatedArticleAuthors.add(newArticleAuthor);
            }

            articleAuthorRepository.saveAll(updatedArticleAuthors);

            article.setArticleAuthors(updatedArticleAuthors);
        }


        Article updatedArticle = articleRepository.save(article);
        return articleMapper.toDTO(updatedArticle);
    }


    public boolean deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("L'article avec l'id " + id + " n'a pas été trouvé"));
        articleAuthorRepository.deleteAll(article.getArticleAuthors());
        articleRepository.delete(article);
        return true;
    }


    public List<ArticleDTO> getArticlesByTitle(String searchTerms){
        List<Article> articles = articleRepository.findByTitle(searchTerms);
        if (articles.isEmpty()) {
            return null;
        }
        return articles.stream().map(articleMapper::toDTO).toList();
    }


    public List<ArticleDTO> getArticlesByContent(String keyword){
        List<Article> articlesByContent = articleRepository.findByContentContainingIgnoreCase(keyword);
        if (articlesByContent.isEmpty()) {
            return null;
        }
        return articlesByContent.stream().map(articleMapper::toDTO).toList();
    }


    public List<ArticleDTO> getArticlesByCreatedAfter(LocalDateTime createdAfter){
        List<Article> articlesByCreatedAfter = articleRepository.findByCreatedAtAfter(createdAfter);
        if (articlesByCreatedAfter.isEmpty()) {
            return null;
        }
        return articlesByCreatedAfter.stream().map(articleMapper::toDTO).toList();
    }


    public List<Article> getFiveLastArticles(){
        List<Article> fiveLastArticles = articleRepository.findByOrderByCreatedAtDesc(Limit.of(5));
        if (fiveLastArticles.isEmpty()) {
            return null;
        }
        return fiveLastArticles;
    }

}
