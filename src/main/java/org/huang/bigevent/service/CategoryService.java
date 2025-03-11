package org.huang.bigevent.service;

import org.huang.bigevent.pojo.Category;

import java.util.List;

public interface CategoryService {
    void addCategory(Category category);
    
    List<Category> selectAllCategories();
    
    Category selectCategoryById(Integer id);
    
    void updateCategory(Category category);
    
    void deleteCategory(Integer id);
}
