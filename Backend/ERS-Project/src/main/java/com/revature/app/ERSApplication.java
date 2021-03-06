package com.revature.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ReimbursementController;
import com.revature.controller.UserController;
import com.revature.exception.ExceptionMapper;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class ERSApplication {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(ERSApplication.class);

		Javalin app = Javalin.create(config -> {

			// 'enableCors' enabling other hosts/ports to be able to use the
			// server
			config.enableCorsForAllOrigins();

			// 'addStaticFiles' to add html, css, and/orr js inside the maven
			// project to interact with them
			config.addStaticFiles("static", Location.CLASSPATH);
		});

		app.before(ctx -> {
			logger.info(ctx.method() + " request recieved to the " + ctx.path() + " endpoint");
		});

		// mapController() take in application and multiple controllers to map
		// each endpoint to the 'app'
		mapControllers(app, new AuthenticationController(), new ReimbursementController(), new UserController());

		ExceptionMapper mapper = new ExceptionMapper();
		mapper.mapExceptions(app);

		app.start(8080);
	}

	// ... operator = zero or more String object may be passed as the
	// argument(s)
	public static void mapControllers(Javalin app, Controller... controllers) {
		for (int i = 0; i < controllers.length; i++) {
			controllers[i].mapEndpoints(app);
		}
	}
}
