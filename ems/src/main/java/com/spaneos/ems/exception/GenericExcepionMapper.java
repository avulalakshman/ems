package com.spaneos.ems.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class GenericExcepionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable throwable) {
		ErrorMessage errorMessage=new ErrorMessage(throwable.getMessage(),500,"http://www.ems.com/doc/excpetion/InternalServerException.html");
		return Response.status(Status.NOT_FOUND).
				entity(errorMessage).build();
	}
}
