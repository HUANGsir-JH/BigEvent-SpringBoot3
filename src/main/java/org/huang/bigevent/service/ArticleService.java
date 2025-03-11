package org.huang.bigevent.service;

import org.huang.bigevent.pojo.Article;
import org.huang.bigevent.pojo.PageBean;

public interface ArticleService {
    void addArticle(Article article);
    
    // 条件分页列表查询
    PageBean<Article> getArticleDetail(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    
    Article selectArticleDetailById(Integer id);
    
    void updateArticle(Article article);
    
    void deleteArticle(Integer id);
}
