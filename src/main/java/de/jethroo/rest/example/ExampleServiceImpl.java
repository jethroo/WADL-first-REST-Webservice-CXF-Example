package de.jethroo.rest.example;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class ExampleServiceImpl implements Thingies {

	private final Gson serializer = new Gson();

	private final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);

	public Response onRetrieve(String id) {
		logger.debug("recieved GET request for thingies with id : "+id);
		return Response.ok(serializer.toJson(Response.ok().build()), MediaType.APPLICATION_JSON).build();
	}

	public void onList(String id) {
		// TODO Auto-generated method stub

	}

	public void onUpdate(String id, String attribute_name) {
		// TODO Auto-generated method stub

	}

	public void onDelete(String id) {
		// TODO Auto-generated method stub

	}

}
