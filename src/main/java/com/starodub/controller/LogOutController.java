package com.starodub.controller;

import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import javax.servlet.http.Cookie;

import static com.starodub.web.filter.UserFilter.COOKIE_NAME;

public class LogOutController implements Controller {

    @Override
    public ViewModel process(Request request) {
        ViewModel vm = ViewModel.of("login");
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return vm;
        }

        for (int i = 0; i < cookies.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals(COOKIE_NAME)) {
                cookies[i].setMaxAge(0);
            }
        }
        vm.setCookies(cookies);
        return vm;
    }
}
