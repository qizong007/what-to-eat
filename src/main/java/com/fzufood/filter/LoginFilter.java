package com.fzufood.filter;

import com.fzufood.entity.Admin;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author qizong007
 * @create 2020/11/24 19:23
 */
@WebFilter(urlPatterns = "/admin/*", filterName = "loginFilter")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = request.getRequestURI();
        if (uri.endsWith("login.html") || uri.endsWith("login")) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin == null){
            response.sendRedirect("/admin/login");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
