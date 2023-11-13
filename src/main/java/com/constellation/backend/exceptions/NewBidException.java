package com.constellation.backend.exceptions;

import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NewBidException extends Exception
implements ExceptionMapper<NewBidException> {
	private static final long serialVersionUID = 1L;

	public NewBidException() {
		super("Create bid Failed, bid must be higher than current price");
	}

	public NewBidException(String message) {
		super(message);
	}

	@Override
	public Response toResponse(NewBidException exception) {
		return Response.status(Response.Status.UNAUTHORIZED) // 401 status code
        .entity(exception.getMessage())
        .type(MediaType.TEXT_PLAIN)
        .build();
	}
}