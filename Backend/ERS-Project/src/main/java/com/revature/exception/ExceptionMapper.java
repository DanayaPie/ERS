package com.revature.exception;

import javax.security.auth.login.FailedLoginException;

import com.revature.dto.MessageDTO;

import io.javalin.Javalin;

public class ExceptionMapper {
	public void mapExceptions(Javalin app) {

		app.exception(FailedLoginException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDTO(e.getMessage()));
		});

		app.exception(UnauthorizedException.class, (e, ctx) -> {
			ctx.status(401);
			ctx.json(new MessageDTO(e.getMessage()));
		});

		app.exception(ReimbursementNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(ReimbursementReceiptNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDTO(e.getMessage()));
		});
		
		app.exception(StatusNotFoundException.class, (e, ctx) -> {
			ctx.status(404);
			ctx.json(new MessageDTO(e.getMessage()));
		});

		app.exception(InvalidParameterException.class, (e, ctx) -> {
			ctx.status(400);
			ctx.json(new MessageDTO(e.getMessage()));
		});
	}
}
