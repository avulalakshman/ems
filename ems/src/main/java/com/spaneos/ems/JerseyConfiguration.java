package com.spaneos.ems;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.spaneos.ems.restapi.EmsRestService;

@Configuration
@ApplicationPath("/api")
public class JerseyConfiguration extends ResourceConfig {
	public JerseyConfiguration() {
		register(EmsRestService.class);
		packages("com.spaneos.ems");
	}
}
