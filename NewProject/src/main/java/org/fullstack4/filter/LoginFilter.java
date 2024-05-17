package org.fullstack4.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
@WebFilter(urlPatterns = {"/member/login", "/member/join", "/member/findpwd", "/member/changepwd"} )
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("auto login check");
        HttpServletRequest req =(HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        if(session.getAttribute("user_id") != null){
            try {
                resp.setContentType("text/html; charset=utf-8");
                PrintWriter w = resp.getWriter();
                w.write("<script>alert('로그인 중에는 이용하실 수 없습니다.');history.go(-1);</script>");
                w.flush();
                w.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        chain.doFilter(request, response);
    }
}