package com.revature.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.constant.EndpointConstants;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.utility.ValidateUtil;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userService;

	public UserController() {
		super();
		this.userService = new UserService();
	}

	// sign up
	private Handler signup = (ctx) -> {
		logger.info("UserController.signUp() invoked.");

		User user = ctx.bodyAsClass(User.class);

		ValidateUtil.verifySignUp(user);

		User u = this.userService.signUp(user);

		ctx.json(u);
	};

	@Override
	public void mapEndpoints(Javalin app) {

		app.post(EndpointConstants.POST_SIGNUP, signup);
	}

}
