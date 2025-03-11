package org.huang.bigevent.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.huang.bigevent.utils.JwtUtil;
import org.huang.bigevent.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.Objects;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    // 请求前，拦截器，验证token
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        // 验证token
        if (JwtUtil.verifyToken(token)) {
            Map<String, Object> userinfo = JwtUtil.parseToken(token);
            ThreadLocalUtil.set(userinfo);
            // 从redis中获取token进行比对
            String key="user:token:"+ userinfo.get("username");
            if (Objects.equals(stringRedisTemplate.opsForValue().get(key), token)) {
                return true;
            }else {
                response.setStatus(401);
                return false;
            }
        } else {
            response.setStatus(401);
            return false;
        }
    }
    
    // 请求结束后，清除ThreadLocal，防止内存泄漏
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadLocalUtil.remove();
    }
}
