package com.spaneos.ems.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
@Provider
public class DuplicateDataExceptionMapper implements ExceptionMapper<DuplicateDataException> {

	@Override
	public Response toResponse(DuplicateDataException exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), Status.CONFLICT.getStatusCode(),
				"http://www.ems.com/doc/excpetion/DuplicateData.html");
		return Response.status(Status.CONFLICT).entity(errorMessage).build();
	}

}
