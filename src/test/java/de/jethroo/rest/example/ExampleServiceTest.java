package de.jethroo.rest.example;

import static org.junit.Assert.fail;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-service-context.xml")
@Transactional
public class ExampleServiceTest {

	@Autowired
	private ThingyDao dao;
	
	@Test
	public void testOnRetrieve() {
		dao.findById(1);
		dao.insert(new Thingy("something"));
		Thingies service = new ExampleServiceImpl();
		Response response = service.onRetrieve(1);
		//fail("Not yet implemented");
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
