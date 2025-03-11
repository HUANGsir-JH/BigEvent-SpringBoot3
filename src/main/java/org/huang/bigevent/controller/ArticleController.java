package org.huang.bigevent.controller;

import org.huang.bigevent.pojo.Article;
import org.huang.bigevent.pojo.PageBean;
import org.huang.bigevent.pojo.Result;
import org.huang.bigevent.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/article")
public class ArticleController {
    
    @Autowired
    private ArticleService articleService;
    
    @PostMapping("/add")
    public Result addArticle(@RequestBody @Validated Article article){
        articleService.addArticle(article);
        return Result.builder().code(0).message("添加文章成功").build();
    }
    
    @GetMapping
    public Result getArticleDetail(Integer pageNum,Integer pageSize,
                                   @RequestParam(required = false) Integer categoryId,
                                   @RequestParam(required = false) String state){
        PageBean<Article> articleDetail = articleService.getArticleDetail(pageNum, pageSize, categoryId, state);
        return Result.builder().code(0).message("查询文章成功").data(articleDetail).build();
    }
    
    @PutMapping
    public Result updateArticle(@RequestBody Article article){
        articleService.updateArticle(article);
        return Result.builder().code(0).message("更新文章成功").build();
    }
    
    @GetMapping("/detail")
    public Result getArticleDetail(Integer id){
        Article article = articleService.selectArticleDetailById(id);
        if(article == null){
            return Result.builder().code(1).message("文章不存在").build();
        }else{
            return Result.builder().code(0).message("查询文章成功").data(article).build();
        }
    }
    
    @DeleteMapping
    public Result deleteArticle(Integer id){
        articleService.deleteArticle(id);
        return Result.builder().code(0).message("删除文章成功").build();
    }
}
