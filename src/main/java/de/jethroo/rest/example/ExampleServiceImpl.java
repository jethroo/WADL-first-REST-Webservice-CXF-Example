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
		try {
			int resource_id = Integer.parseInt(id);
			// just generate a dummy resource for this request 
			Thingy somethingy = new Thingy(resource_id, "attribute_value_for_id"+resource_id);

			return Response.ok(serializer.toJson(somethingy), MediaType.APPLICATION_JSON).build();
		} catch (java.lang.NumberFormatException e) {
			return Response.status(400).build();
		}
		
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
