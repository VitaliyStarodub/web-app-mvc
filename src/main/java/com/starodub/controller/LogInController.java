package com.starodub.controller;

import com.starodub.model.User;
import com.starodub.service.UserService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

import javax.servlet.http.Cookie;

public class LogInController implements Controller {

    private final UserService userService;

    public LogInController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        String email = request.getParamsByName("email");
        String password = request.getParamsByName("password");
        User user = userService.findByEmail(email);
        boolean isVerified = userService.validatePassword(user, password);
        ViewModel vm = null;

        if (user != null && isVerified) {
           vm = processAuthorised(user);
        } else {
            vm = processUnauthorised();
        }

        return vm;
    }

    private ViewModel processUnauthorised() {
        ViewModel vm = ViewModel.of("login");
        vm.addAttribute("msg", true);
        return vm;
    }

    private ViewModel processAuthorised(User user) {
        ViewModel vm = ViewModel.of("home");
        Cookie cookie = new Cookie("MATE", user.getToken());
        vm.addCookie(cookie);
        vm.addAttribute("user", user);

        return vm;
    }




}
