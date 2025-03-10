package org.wildcodeschool.myblog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.model.*;

@Mapper(componentModel="spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorMapper {

    @Mapping(source = "author", target = "id", ignore = true)
    Author from(ArticleAuthor articleAuthor);

    Author toEntity(AuthorDTO authorDTO);
    AuthorDTO toDTO(Author author);

    void copy(AuthorDTO authorDTO, @MappingTarget Author author);
}
