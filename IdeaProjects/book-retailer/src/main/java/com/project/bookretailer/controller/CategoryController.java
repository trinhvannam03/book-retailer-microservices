package com.project.bookretailer.controller;

import com.project.bookretailer.dto.CategoryDTO;
import com.project.bookretailer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public List<CategoryDTO> getCategory() {
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesAsDTOs();
        System.out.println(categoryDTOList);
        return categoryDTOList;
    }
}
