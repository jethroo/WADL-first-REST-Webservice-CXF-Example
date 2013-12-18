package de.jethroo.rest.example;

import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

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
		Assert.assertEquals(200,response.getStatus());
	}

	/*
	@Test
	public void testOnList() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testOnDelete() {
		fail("Not yet implemented");
	}
	*/
}
