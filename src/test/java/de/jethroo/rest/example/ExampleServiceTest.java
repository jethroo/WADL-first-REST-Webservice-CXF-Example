package de.jethroo.rest.example;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-service-context.xml")
@Transactional
public class ExampleServiceTest {

	@Autowired
	private ThingyDao dao;

	@Autowired
	private Thingies service;

	@Test
	public void testOnRetrieve() {
		Thingy thingy = new Thingy("something");
		dao.insert(thingy);
		Response response = service.onRetrieve(thingy.getId());
		Gson gson = new Gson();
		Thingy response_thingy = gson.fromJson(response.getEntity().toString(), Thingy.class);
		Assert.assertEquals(thingy.getId(), response_thingy.getId());
		Assert.assertEquals(thingy.getAttribute_name(), response_thingy.getAttribute_name());
		Assert.assertEquals(200, response.getStatus());
	}

	@Test
	public void testOnList() {
		// create som thingies
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
		Response response = service.onList();
		Type listType = new TypeToken<ArrayList<Thingy>>() {
		}.getType();
		List<Thingy> response_thingies = new Gson().fromJson(response.getEntity().toString(), listType);
		Assert.assertEquals(thingies.size(), response_thingies.size());
		//retain all should remove al equal elements thus our list should be emtpy afterwards
		thingies.retainAll(response_thingies);
		Assert.assertEquals(true, thingies.isEmpty());
	}
	/*
	 * @Test public void testOnUpdate() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testOnDelete() { fail("Not yet implemented"); }
	 */
}
