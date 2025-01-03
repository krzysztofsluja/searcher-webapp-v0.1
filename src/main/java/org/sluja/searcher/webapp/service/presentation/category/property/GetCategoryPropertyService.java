package org.sluja.searcher.webapp.service.presentation.category.property;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.annotation.log.noobject.MethodStartLog;
import org.sluja.searcher.webapp.annotation.log.object.ObjectMethodEndLog;
import org.sluja.searcher.webapp.dto.presentation.category.property.CategoryPropertyDto;
import org.sluja.searcher.webapp.mapper.category.CategoryPropertyEntityMapper;
import org.sluja.searcher.webapp.mapper.category.CategoryPropertyMapper;
import org.sluja.searcher.webapp.repository.category.property.CategoryPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetCategoryPropertyService {

    @Autowired
    private final CategoryPropertyMapper categoryPropertyMapper;
    private final CategoryPropertyRepository categoryPropertyRepository;

    @MethodStartLog
    @ObjectMethodEndLog
    public List<CategoryPropertyDto> findPropertiesForCategories(final List<String> categoryNames, final String context) {
        return categoryPropertyRepository
                .findPropertiesForCategories(categoryNames, context)
                .stream()
                .map(CategoryPropertyEntityMapper::map)
                .map(property -> categoryPropertyMapper.toDto(property, context))
                .toList();
    }


}
