package org.wildcodeschool.myblog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.Author;

@Data
public class ArticleAuthorDTO {
    @NotNull(message = "L'ID de l'auteur ne doit pas être nul")
    @Positive(message = "L'ID de l'auteur doit être un nombre positif")
    private Long id;
    private Article article;
    private Author author;

    @NotBlank(message = "La contribution de l'auteur ne doit pas être vide")
    private String contribution;
}
