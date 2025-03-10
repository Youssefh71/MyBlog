package org.wildcodeschool.myblog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.util.List;

@Data
public class AuthorDTO {

    @NotNull(message = "L'ID de l'auteur ne doit pas être nul")
    @Positive(message = "L'ID de l'auteur doit être un nombre positif")
    private Long id;
    private String firstname;
    private String lastname;
    private List<ArticleAuthorDTO> articleAuthors;

}
