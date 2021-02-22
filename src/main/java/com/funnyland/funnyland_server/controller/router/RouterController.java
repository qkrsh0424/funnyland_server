package com.funnyland.funnyland_server.controller.router;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouterController implements ErrorController{
    @RequestMapping("/error")
	public String handleError() {
		return "/index.html";
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}
}
