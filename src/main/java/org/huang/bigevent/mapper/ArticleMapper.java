package org.huang.bigevent.mapper;

import org.apache.ibatis.annotations.*;
import org.huang.bigevent.pojo.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
    
    @Insert("insert into article (title,content,cover_img,state,category_id,create_user,create_time,update_time) " +
            "values " +
            "(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},#{createTime},#{updateTime})")
    void addArticle(Article article);
    
    @Select("select * from article where create_user = #{userId}")
    void selectAllArticles(Integer userId);
    
    @Select("select * from article where id = #{id} and create_user = #{userId}")
    Article selectArticleById(Integer id,Integer userId);
    
    List<Article> selectArticlePages(Integer id, Integer categoryId, String state);
    
    @Update("update article set title=#{article.title},content=#{article.content},cover_img=#{article.coverImg}," +
            "state=#{article.state},category_id=#{article.categoryId} where id=#{article.id} and create_user = #{userId}")
    void updateArticle(@Param("article") Article article, @Param("userId") Integer userId);
    
    @Delete("delete from article where id = #{id}")
    void deleteArticle(Integer id);
}
