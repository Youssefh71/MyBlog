package org.wildcodeschool.myblog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.model.*;

import java.util.Optional;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ArticleMapper {

    @Mapping(source = "image.url", target = "id", ignore = true)
    Article from(Image image, ArticleAuthor articleAuthor, Category category);

    Article toEntity(ArticleCreateDTO articleDTO);
    ArticleDTO toDTO(Article article);

    void copy(ArticleDTO articleDTO, @MappingTarget Article article);
}
