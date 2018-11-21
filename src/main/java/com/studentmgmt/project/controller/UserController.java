package com.studentmgmt.project.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.studentmgmt.project.dto.UserDTO;
import com.studentmgmt.project.model.User;
import com.studentmgmt.project.service.UserService;
import com.studentmgmt.project.util.ConversionUtil;

import ch.qos.logback.core.pattern.ConverterUtil;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/login");
		return modelAndView;
	}

	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("user/signup");

		return modelAndView;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public ModelAndView createUser(@Valid User user,@ModelAttribute("confirmPassword") String confirmPassword, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();

		User userExists = userService.findByEmail(user.getEmail());

		if (userExists != null) {
			bindingResult.rejectValue("email", "error.user", "This Email already exists");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("user/signup");
		} else {
			userService.saveUser(user);
			modelAndView.addObject("msg", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("user/signup");
		}

		return modelAndView;

	}

	@RequestMapping(value = "/home/home", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.findByEmail(auth.getName());

		modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
		modelAndView.setViewName("/home/home");

		return modelAndView;

	}

	@RequestMapping(value = "access_denied", method = RequestMethod.GET)
	public ModelAndView accessDenied() {
		ModelAndView modelAndView = new ModelAndView();

		modelAndView.setViewName("error/access_denied");

		return modelAndView;

	}
}
