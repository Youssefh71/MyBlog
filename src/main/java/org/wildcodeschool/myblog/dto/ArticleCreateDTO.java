package org.wildcodeschool.myblog.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class ArticleCreateDTO {

    @NotBlank(message = "Le titre ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le titre doit contenir entre 2 et 50 caractères")
    private String title;

    @NotBlank(message = "Le contenu ne doit pas être vide")
    @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
    private String content;

    @NotNull(message = "L'ID de la catégorie ne doit pas être nul")
    @Positive(message = "L'ID de la catégorie doit être un nombre positif")
    private Long categoryId;

    @NotEmpty(message = "La liste des images ne doit pas être vide")
    private List<@Valid ImageDTO> images;

    @NotEmpty(message = "La liste des auteurs ne doit pas être vide")
    private List<@Valid ArticleAuthorDTO> authors;
}
