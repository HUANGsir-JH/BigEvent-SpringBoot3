package org.huang.bigevent.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
@Builder
public class Article {
    private Integer id;//主键ID
    @NotEmpty
    @Pattern(regexp = "^.{1,20}$")
    private String title;//文章标题
    @NotEmpty
    private String content;//文章内容
    @URL
    private String coverImg;//封面图像
    @NotEmpty
    @Pattern(regexp = "^(已发布|草稿)$")
    private String state;//发布状态 已发布|草稿
    @NotNull
    private Integer categoryId;//文章分类id
    private Integer createUser;//创建人ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
}
