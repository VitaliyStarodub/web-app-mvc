package com.starodub.web.filter;

import com.starodub.dao.UserDao;
import com.starodub.model.Role;
import com.starodub.model.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.starodub.Factory.getUserDao;
import static com.starodub.model.Role.RoleName.USER;
import static com.starodub.model.Role.RoleName.ADMIN;


public class UserFilter implements Filter {

    private final Map<String, Role.RoleName> protectedUriMap = new HashMap<>();

    private UserDao userDao;

    public static final String COOKIE_NAME = "MATE";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userDao = getUserDao();
        protectedUriMap.put("/servlet/categories", USER);
        protectedUriMap.put("/servlet/category", USER);
        protectedUriMap.put("/servlet/products", USER);
        protectedUriMap.put("/servlet/product", USER);
        protectedUriMap.put("/servlet/home", USER);
        protectedUriMap.put("/servlet/admin", ADMIN);
        protectedUriMap.put("/servlet/admin/categories", ADMIN);
        protectedUriMap.put("/servlet/admin/products", ADMIN);
        protectedUriMap.put("/servlet/admin/add-product", ADMIN);
        protectedUriMap.put("/servlet/admin/delete-product", ADMIN);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        Cookie[] cookies = req.getCookies();
        Role.RoleName roleName = protectedUriMap.get(req.getRequestURI());
        String token = null;
        User user = null;

        if(cookies == null) {
            processUnauthenticated(request, response);
            return;
        }

        if (roleName == null) {
            processAuthenticated(request, response, chain);
            return;
        }

        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                token = cookie.getValue();
            }
        }

        if (token == null) {
            processUnauthenticated(request, response);
        } else {
            user = userDao.findByToken(token);
            if (user == null) {
                processUnauthenticated(request, response);
            } else {
                setUser(req, user);
                if (verifyRole(user, roleName)) {
                    processAuthenticated(request, response, chain);
                } else {
                    processAccessDenied(request, response);
                }
            }
        }

    }

    private void processAccessDenied(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(request, response);
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream()
                .map(Role::getRoleName)
                .anyMatch(r -> r.equals(roleName));
    }

    private void setUser(HttpServletRequest req, User user) {
        req.setAttribute("user", user);
    }

    private void processAuthenticated(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    private void processUnauthenticated(ServletRequest request, ServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    public void destroy() {

    }
}
