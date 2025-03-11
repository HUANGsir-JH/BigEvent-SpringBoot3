## BigEvent - 后端
项目编写基于[该视频](https://www.bilibili.com/video/BV14z4y1N7pg/?spm_id_from=333.1365.top_right_bar_window_custom_collection.content.click&vd_source=45792527913efdcbf520573d0c16b421)进行编写。本项目仅为后端项目，前端项目移至[BigEvent - 前端](https://github.com/HUANGsir-JH/BigEvent-Vue3) 

## 技术栈
- Java基础
- Spring6
- SpringMvc
- Mybatis （基于MySQL）
- SpringBoot3
- Jwt令牌技术
- Redis服务 [windows版](https://github.com/redis-windows/redis-windows)
- [阿里云OSS服务](https://www.aliyun.com/product/oss?scm=20140722.S_product@@%E4%BA%91%E4%BA%A7%E5%93%81@@102633._.RL_oss-LOC_topbar~UND~product-OR_ser-PAR1_215041fa17416682764897920e1881-V_4-RE_productNew-P0_0-P1_0)

## 收获
1. 能够基于SpringBoot编写接口，分为Controller、Service、Mapper三层进行对应分离
2. 基于mybatis的数据库操作，包括注解和动态sql
3. Jwt令牌技术的使用，进行用户的身份验证
4. 拦截器的使用，再请求之前进行各类验证
5. 使用redis进行常用信息的存储，或者进行信息二次比对
6. pageHelper的使用，用来简化分页查询的处理
7. 异常处理器的使用
