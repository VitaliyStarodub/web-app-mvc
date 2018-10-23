package com.starodub.controller;

import com.starodub.model.User;
import com.starodub.service.UserService;
import com.starodub.web.Request;
import com.starodub.web.ViewModel;

public class RegisterController implements Controller {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ViewModel process(Request request) {
        String email = request.getParamsByName("email");
        String password = request.getParamsByName("password");
        User user = new User(email, password);
        ViewModel vm = ViewModel.of("welcome");
        vm.addAttribute("user", userService.addUser(user));

        return vm;
    }
}
