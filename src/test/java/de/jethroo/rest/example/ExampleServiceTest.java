package de.jethroo.rest.example;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-service-context.xml")
@Transactional
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ExampleServiceTest {

	@Autowired
	private ThingyDao dao;

	@Autowired
	private Thingies service;

	private Gson gson = new Gson();

	@Test
	public void testOnRetrievePresentThingy() {
		Thingy thingy = new Thingy("something");
		dao.insert(thingy);
		Response response = service.onRead(thingy.getId());
		Thingy response_thingy = gson.fromJson(response.getEntity().toString(), Thingy.class);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(thingy.getId(), response_thingy.getId());
		Assert.assertEquals(thingy.getAttribute_name(), response_thingy.getAttribute_name());
	}

	@Test
	public void testOnUnknownThingy() {
		Response response = service.onRead(1234);
		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testOnList() {
		// create some thingies
		List<Thingy> thingies = new ArrayList<Thingy>();
		for (int i = 1; i < 5; i++) {
			thingies.add(new Thingy(i, "foo_" + i));
		}
		// save them in db
		Iterator<Thingy> i = thingies.iterator();
		while (i.hasNext()) {
			Thingy current = i.next();
			dao.insert(current);
		}
		// see what we get then calling onList of service
		Response response = service.onIndex();
		Assert.assertEquals(200, response.getStatus());
		Type listType = new TypeToken<ArrayList<Thingy>>() {
		}.getType();
		List<Thingy> response_thingies = gson.fromJson(response.getEntity().toString(), listType);
		Assert.assertEquals(thingies.size(), response_thingies.size());
		// retain all should remove all equal elements thus our list should be
		// empty afterwards
		thingies.retainAll(response_thingies);
		Assert.assertEquals(true, thingies.isEmpty());
	}

	@Test
	public void testOnUpdatePresentThingy() {
		Thingy thingy = new Thingy("something");
		dao.insert(thingy);
		thingy.setAttribute_name("something different");
		Response response = service.onUpdate(thingy.getId(), thingy.getAttribute_name());
		Thingy response_thingy = gson.fromJson(response.getEntity().toString(), Thingy.class);
		Assert.assertEquals(200, response.getStatus());
		Assert.assertEquals(thingy.getId(), response_thingy.getId());
		Assert.assertEquals(thingy.getAttribute_name(), response_thingy.getAttribute_name());
		Assert.assertEquals(dao.findById(thingy.getId()).getAttribute_name(), response_thingy.getAttribute_name());
	}

	@Test
	public void testOnUpdateUnknownThingy() {
		Thingy thingy = new Thingy("something");
		Response response = service.onUpdate(1234, thingy.getAttribute_name());
		Assert.assertEquals(404, response.getStatus());
	}

	@Test
	public void testOnDeletePresentThingy() {
		Thingy thingy = new Thingy("something");
		dao.insert(thingy);
		Response response = service.onDelete(thingy.getId());
		Thingy response_thingy = gson.fromJson(response.getEntity().toString(), Thingy.class);
		//check if thingy is really deleted
		Assert.assertEquals(null,dao.findById(response_thingy.getId()));
		
	}
	
	@Test
	public void testOnDeleteUnknownThingy() {
		Response response = service.onDelete(1234);
		Assert.assertEquals(404, response.getStatus());
	}

}
