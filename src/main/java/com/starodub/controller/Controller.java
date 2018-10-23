package com.starodub.controller;

import com.starodub.web.Request;
import com.starodub.web.ViewModel;

public interface Controller {

    ViewModel process(Request request);

}
