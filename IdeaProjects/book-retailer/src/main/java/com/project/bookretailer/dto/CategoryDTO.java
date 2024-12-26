package com.project.bookretailer.dto;

import com.project.bookretailer.entity.Category;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private long categoryId;
    private String categoryName;
    private String categoryDesc;
    private long parentId;
    private List<CategoryDTO> children = new ArrayList<>();


    public long getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public long getParentId() {
        return parentId;
    }

    public List<CategoryDTO> getChildren() {
        return children;
    }

    public static CategoryDTO convertFromCategory(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setCategoryDesc(category.getCategoryDesc());
        categoryDTO.setParentId(category.getParentId());
        categoryDTO.setChildren(new ArrayList<>());
        return categoryDTO;
    }
}
