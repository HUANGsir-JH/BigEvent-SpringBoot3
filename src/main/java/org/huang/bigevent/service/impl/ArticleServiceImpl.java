package org.huang.bigevent.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.huang.bigevent.mapper.ArticleMapper;
import org.huang.bigevent.pojo.Article;
import org.huang.bigevent.pojo.PageBean;
import org.huang.bigevent.service.ArticleService;
import org.huang.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {
    
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public void addArticle(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        // 获取写文章的用户的id
        Map<String,Object> userinfo = ThreadLocalUtil.get();
        article.setCreateUser((Integer) userinfo.get("id"));
        // 调用mapper层的方法
        articleMapper.addArticle(article);
    }
    
    @Override
    public PageBean<Article> getArticleDetail(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 创建PageBean对象
        PageBean<Article> pageBean = new PageBean<>();
        // 设置当前页码 和 每页显示的条数 PageHelper.startPage(pageNum,pageSize);
        PageHelper.startPage(pageNum,pageSize);
        // 调用mapper层的方法查询文章列表
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        Integer id = (Integer) userinfo.get("id");
        // 进行强转。PageHelper 会将查询结果封装成 Page 对象，其中包含了分页信息（总记录数、总页数等）
        Page<Article> page = (Page<Article>) articleMapper.selectArticlePages(id,categoryId,state);
        // 把数据封装到PageBean对象中
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        
        return pageBean;
    }
    
    @Override
    public Article selectArticleDetailById(Integer id) {
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        Integer userId = (Integer) userinfo.get("id");
        return articleMapper.selectArticleById(id,userId);
    }
    
    @Override
    public void updateArticle(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> userinfo = ThreadLocalUtil.get();
        articleMapper.updateArticle(article,(Integer) userinfo.get("id"));
    }
    
    @Override
    public void deleteArticle(Integer id) {
//        Map<String, Object> userinfo = ThreadLocalUtil.get();
        articleMapper.deleteArticle(id);
    }
}
