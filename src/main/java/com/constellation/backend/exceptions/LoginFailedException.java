package com.constellation.backend.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class LoginFailedException extends Exception
        implements ExceptionMapper<LoginFailedException> {
    private static final long serialVersionUID = 1L;

    public LoginFailedException() {
        super("Login failed due to invalid credentials.");
    }

    public LoginFailedException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(LoginFailedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED) // 401 status code
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}