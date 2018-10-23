package com.starodub.web;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewModel {

    private final String view;
    private final Map<String, Object> model = new HashMap<>();
    private Cookie[] cookies = new Cookie[]{};

    public ViewModel(String view) {
        this.view = view;
    }

    public String getView() {
        return view;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void addAttribute(String name, Object obj) {
        this.model.put(name, obj);
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public void addCookie(Cookie cookie) {
        Cookie[] newCookies = Arrays.copyOf(this.cookies, this.cookies.length + 1);
        newCookies[newCookies.length - 1] = cookie;
        this.cookies = newCookies;
    }

    public static ViewModel of(String view) {
        return new ViewModel(view);
    }
}
