package org.wildcodeschool.myblog.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
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
}
