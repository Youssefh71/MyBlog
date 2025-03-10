package org.wildcodeschool.myblog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;
import java.util.List;

@Data
public class ImageDTO {
    private Long id;

    @URL(message = "L'URL de l'image doit être valide")
    private String url;
    private List<Long> articleIds;
}
