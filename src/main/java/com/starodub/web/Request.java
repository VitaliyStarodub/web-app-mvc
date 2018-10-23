package com.starodub.web;

import javax.servlet.http.Cookie;
import java.util.Map;

public class Request {

    private final String method;
    private final String uri;
    private final Map<String, String[]> params;
    private final Cookie[] cookies;

    public Request(String method, String uri, Map<String, String[]> params, Cookie[] cookies) {
        this.method = method;
        this.uri = uri;
        this.params = params;
        this.cookies = cookies;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public String getParamsByName(String name) {
        return params.get(name)[0];
    }

    public static Request of(String method, String uri) {
        return new Request(method, uri, null, null);
    }

    public static Request of(String method, String uri, Map<String, String[]> params) {
        return new Request(method, uri, params, null);
    }

    public static Request of(String method, String uri, Map<String, String[]> params, Cookie[] cookies) {
        return new Request(method, uri, params, cookies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (method != null ? !method.equals(request.method) : request.method != null) return false;
        return uri != null ? uri.equals(request.uri) : request.uri == null;
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (uri != null ? uri.hashCode() : 0);
        return result;
    }
}
