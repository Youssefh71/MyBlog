package org.wildcodeschool.myblog.dto;

import lombok.Data;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private List<ArticleDTO> articles;

}
