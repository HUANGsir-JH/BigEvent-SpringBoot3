package org.huang.bigevent.service.impl;

import org.huang.bigevent.mapper.CategoryMapper;
import org.huang.bigevent.pojo.Category;
import org.huang.bigevent.service.CategoryService;
import org.huang.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public void addCategory(Category category) {
        Map<String,Object> user  = ThreadLocalUtil.get();
        category.setCreateUser((Integer) user.get("id"));
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.addCategory(category);
    }
    
    @Override
    public List<Category> selectAllCategories() {
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        var userid = (Integer) userinfo.get("id");
        return categoryMapper.selectAllCategories(userid);
    }
    
    @Override
    public Category selectCategoryById(Integer id) {
        return categoryMapper.selectCategoryById(id);
    }
    
    @Override
    public void updateCategory(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.updateCategory(category);
    }
    
    @Override
    public void deleteCategory(Integer id) {
        categoryMapper.deleteCategory(id);
    }
}
