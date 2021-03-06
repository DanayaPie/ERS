package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.constant.EndpointConstants;
import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.model.User;
import com.revature.service.UserService;
import com.revature.utility.ValidateUtil;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class AuthenticationController implements Controller {

	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	private UserService userService;

	// constructor
	public AuthenticationController() {
		super();
		this.userService = new UserService();
	}

	private Handler login = (ctx) -> {
		logger.info("AuthenticationController.login() invoked");

		LoginDTO loginDTO = ctx.bodyAsClass(LoginDTO.class);

//		this.userService.verifyUser(loginDTO.getUsername(), loginDTO.getPassword());

		logger.debug("username and pass {} {}", loginDTO.getUsername(), loginDTO.getPassword());
		
		ValidateUtil.verifyUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

		User user = this.userService.getUserByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

		/*-
		 * 	HttpSession keep track of which client is sending requests 
		 *  HttpSession objects identified certain client (though cookies) to perform certain different attributes
		 */
		HttpServletRequest req = ctx.req;
		HttpSession session = req.getSession();

		session.setAttribute("currentUser", user); // set the 'currentUser' to
													// the attribute

		ctx.json(user);
	};
	
	private Handler logout = (ctx) -> {
		logger.info("AuthenticationController.logout() invoked");
		
		// keep track of which client is sending request
		HttpServletRequest req = ctx.req;
		
		// invalidate or logout the user
		req.getSession().invalidate();
		
		ctx.json("You've been logged out");
	};
	
	private Handler checkLoginStatus = (ctx) -> {
		logger.info("AuthenticationController.checkLoginStatus() invoked");
		
		HttpSession session = ctx.req.getSession();
		
		// check if session.getAttribute("currentUser") is null or not
		if (!(session.getAttribute("currentUser") == null)) {
			ctx.json(session.getAttribute("currentUser"));
			ctx.status(200);
		} else {
			ctx.json(new MessageDTO("User is currently looged in"));
			ctx.status(401);
		}
	};

	@Override
	public void mapEndpoints(Javalin app) {
		app.post(EndpointConstants.POST_LOGIN, login);
		app.post(EndpointConstants.POST_LOGOUT, logout);
		app.get(EndpointConstants.GET_CHECKLOGINSTATUS, checkLoginStatus);
	}
}
