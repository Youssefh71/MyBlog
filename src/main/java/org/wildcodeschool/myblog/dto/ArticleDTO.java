package org.wildcodeschool.myblog.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {

    private Long id;

    @NotBlank(message = "Le titre ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
    private String title;

    @NotBlank(message = "Le contenu ne doit pas être vide")
    @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
    private String content;
    private LocalDateTime updatedAt;
    private String categoryName;

    @NotEmpty(message = "La liste des images ne doit pas être vide")
    private List<ImageDTO> imageUrls;

    @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
    private List<AuthorDTO> authors;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ImageDTO> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<ImageDTO> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }

}
