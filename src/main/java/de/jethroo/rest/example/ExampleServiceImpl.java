package de.jethroo.rest.example;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

public class ExampleServiceImpl implements Thingies {

	private final Gson serializer = new Gson();

	private final Logger logger = LoggerFactory.getLogger(ExampleServiceImpl.class);
	
	@Autowired
	private ThingyDao dao;
	
	public void setDao(ThingyDao dao) {
		this.dao = dao;
	}

	@Override
	public Response onRead(int id) {
		logger.debug("recieved GET request for thingies with id : " + id);
		Thingy result;
		if (id > 0) {
			result = dao.findById(id);
			if (result != null) {
				return Response.ok(serializer.toJson(result), MediaType.APPLICATION_JSON).build();
			}
		}
		return Response.status(404).build();
	}

	@Override
	public Response onIndex() {
		logger.debug("recieved GET request for thingies (index)");
		return Response.ok(serializer.toJson(dao.selectAll()), MediaType.APPLICATION_JSON).build();
	}

	@Override
	public Response onUpdate(int id, String attribute_name) {
		logger.debug("recieved PUT request for thingy with id : " + id);
		Thingy thing = dao.findById(id);
		if (thing != null) {
			thing.setAttribute_name(attribute_name);
			dao.saveOrUpdate(thing);
			return Response.ok(serializer.toJson(thing), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(404).build();
		}
	}

	@Override
	public Response onDelete(int id) {
		logger.debug("recieved DELETE request for thingy with id : " + id);
		Thingy thing = dao.findById(id);
		if (thing != null) {
			dao.delete(thing);
			return Response.ok(serializer.toJson(thing), MediaType.APPLICATION_JSON).build();
		} else {
			return Response.status(404).build();
		}
	}

	@Override
	public Response onCreate(String attribute_name) {
		logger.debug("recieved POST request for thingy, attribute_name: "+attribute_name);
		if (attribute_name != null){
			Thingy thingy = new Thingy(attribute_name);
			dao.saveOrUpdate(thingy);
			return Response.ok(serializer.toJson(thingy), MediaType.APPLICATION_JSON).build();
		}
		return Response.status(400).build();
	}

}
