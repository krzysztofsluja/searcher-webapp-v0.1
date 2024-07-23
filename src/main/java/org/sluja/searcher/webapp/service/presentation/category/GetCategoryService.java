package org.sluja.searcher.webapp.service.presentation.category;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.presentation.category.CategoryDto;
import org.sluja.searcher.webapp.mapper.category.CategoryMapper;
import org.sluja.searcher.webapp.repository.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetCategoryService {

    private final CategoryRepository categoryRepository;
    @Autowired
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        //TODO logging
        return categoryRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(categoryMapper::toDto)
                .toList();
    }

}
