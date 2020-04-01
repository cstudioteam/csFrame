package com.handywedge.calendar.Office365.rest.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RequestExceptionMapper implements ExceptionMapper<RequestException> {

    @Override
    public Response toResponse(RequestException e) {
        return Response.status( Response.Status.BAD_REQUEST)
                .entity(new ApiResponseMessage( ))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
