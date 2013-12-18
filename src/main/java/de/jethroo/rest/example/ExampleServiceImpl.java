package de.jethroo.rest.example;

import java.util.ArrayList;
import java.util.List;

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

	public Response onRetrieve(int id) {
		logger.debug("recieved GET request for thingies with id : " + id);
		Thingy result;
		if (id > 0) {
			result = dao.findById(id);
			if (result != null) {
				return Response.ok(serializer.toJson(result), MediaType.APPLICATION_JSON).build();
			}
		}
		return Response.noContent().build();
	}

	public Response onList() {
		logger.debug("recieved GET request for thingies (index)");

		// just generate a dummy resource for this request
		List<Thingy> thingies = new ArrayList<Thingy>();
		for (int i = 1; i < 5; i++) {
			thingies.add(new Thingy(i, "foo_" + i));
		}
		return Response.ok(serializer.toJson(thingies), MediaType.APPLICATION_JSON).build();
	}

	public Response onUpdate(int id, String attribute_name) {
		ThingyDao dao = new ThingyDao();
		Thingy thing = dao.findById(id);
		if (thing != null) {
			thing.setAttribute_name(attribute_name);
		} else {
			thing = new Thingy(attribute_name);
		}
		dao.saveOrUpdate(thing);
		return Response.ok(serializer.toJson(null), MediaType.APPLICATION_JSON).build();
	}

	public Response onDelete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
