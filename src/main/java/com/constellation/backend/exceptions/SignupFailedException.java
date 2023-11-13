package com.constellation.backend.exceptions;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class SignupFailedException extends Exception
        implements ExceptionMapper<SignupFailedException> {
    private static final long serialVersionUID = 1L;

    public SignupFailedException() {
        super("Signup failed, user already exists");
    }

    public SignupFailedException(String message) {
        super(message);
    }

    @Override
    public Response toResponse(SignupFailedException exception) {
        return Response.status(Response.Status.UNAUTHORIZED) // 401 status code
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}