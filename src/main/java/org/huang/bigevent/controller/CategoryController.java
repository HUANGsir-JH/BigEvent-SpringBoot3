package org.huang.bigevent.controller;

import jakarta.validation.constraints.NotNull;
import org.huang.bigevent.pojo.Category;
import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public Result getList(){
        return Result.builder().code(0).message("获取列表成功")
                .data(categoryService.selectAllCategories()).build();
    }
    
    @PostMapping("/add")
    public Result addCategory(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.addCategory(category);
        return Result.builder().code(0).message("添加成功").build();
    }
    
    @GetMapping("/detail")
    public Result getDetail(@NotNull Integer id){
        return Result.builder().code(0).message("获取成功")
                .data(categoryService.selectCategoryById(id)).build();
    }
    
    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.updateCategory(category);
        return Result.builder().code(0).message("更新成功").build();
    }
    
    @DeleteMapping
    public Result deleteCategory(Integer id){
        categoryService.deleteCategory(id);
        return Result.builder().code(0).message("删除分类成功").build();
    }

}
