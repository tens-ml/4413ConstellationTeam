package com.constellation.backend.exceptions;

import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class WrongUserException extends Exception
implements ExceptionMapper<WrongUserException> {
	private static final long serialVersionUID = 1L;

	public WrongUserException() {
		super("Pay Now failed, you are not the user who owns the bid");
	}

	public WrongUserException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(WrongUserException exception) {
		return Response.status(Response.Status.UNAUTHORIZED) // 401 status code
        .entity(exception.getMessage())
        .type(MediaType.TEXT_PLAIN)
        .build();
	}
}
