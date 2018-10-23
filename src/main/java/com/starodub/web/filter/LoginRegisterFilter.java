package com.starodub.web.filter;

import com.starodub.dao.UserDao;
import com.starodub.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.starodub.Factory.getUserDao;

public class LoginRegisterFilter implements Filter {

    private final Set<String> uriSet = new HashSet<>();

    private UserDao userDao;

    public static final String COOKIE_NAME = "MATE";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = getUserDao();
        uriSet.add("/servlet/login");
        uriSet.add("/servlet/register");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        String token = null;
        User user = null;

        if (cookies == null) {
            chain.doFilter(request, response);
            return;
        }

        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                token = cookie.getValue();
            }
        }

        if (token != null) {
            user = userDao.findByToken(token);
        }

        if (user != null && uriSet.contains(req.getRequestURI())) {
            req.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
