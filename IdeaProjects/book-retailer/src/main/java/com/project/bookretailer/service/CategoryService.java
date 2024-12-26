package com.project.bookretailer.service;

import com.project.bookretailer.dto.CategoryDTO;
import com.project.bookretailer.entity.Category;
import com.project.bookretailer.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO getCategoriesAsTree(Category parent, List<Category> categories, List<Category> refinedCategories) {
        CategoryDTO categoryDTO = CategoryDTO.convertFromCategory(parent);
        refinedCategories.remove(parent);
        for (Category category : categories) {
            if (category.getParentId() == parent.getCategoryId()) {
                refinedCategories.remove(category);
                categoryDTO.getChildren().add(getCategoriesAsTree(category, categories, refinedCategories));
            }
        }
        categories = new ArrayList<>(refinedCategories);
        return categoryDTO;
    }

    public List<CategoryDTO> getAllCategoriesAsDTOs() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        List<Category> refinedCategories = new ArrayList<>(categories);
        for (Category category : categories) {
            if (category.getParentId() == 0) {
                categoryDTOs.add(getCategoriesAsTree(category, categories, refinedCategories));
            }
        }
        return categoryDTOs;
    }
}
