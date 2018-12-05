package com.boeing.drone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.sql.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DroneControllerIT {

	@Autowired
	private DroneRepository droneRepository;

	@Autowired
	private MockMvc mvc;

	private ObjectMapper mapper = new ObjectMapper();
	final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Before
	public void before() {
		droneRepository.deleteAll();
	}

	@After
	public void after() {
		droneRepository.deleteAll();
	}

	@Test
	public void testGetAll() throws Exception {
		// Setup
		Drone testDrone = new Drone();
		testDrone.setTitle("QuadCopter");
		droneRepository.save(testDrone);

		// Exercise
		String response = mvc.perform(get("/drone")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();

		List<Drone> result = mapper.readValue(response, new TypeReference<List<Drone>>() {
		});

		// Assert

		Assert.assertEquals("GET should reutrn one item", 1, result.size());
		Drone actual = result.get(0);
		Assert.assertTrue("GET response should match what is in Drone DB",
				testDrone.getTitle().equals(actual.getTitle()));
	}

	@Test
	public void testSet() throws Exception {
		// Setup
		String jsonString = "{\"title\":\"Helicopter\", \"firstFlight\": \"2018-12-04\"}";

		// Exercise
		mvc.perform(post("/drone").contentType(MediaType.APPLICATION_JSON).content(jsonString)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	public void testDelete() throws Exception {
		// Setup
		Drone newDrone = new Drone("Helicopter", new Date(2018 - 12 - 04));
		droneRepository.save(newDrone);
		String jsonString = "{\"title\":\"Helicopter\", \"firstFlight\": \"2018-12-04\"}";
		Long id = newDrone.getId();
		
		int sizeBeforeDelete = droneRepository.findAll().size();

		// Exercise
		mvc.perform(delete("/drone/" + id).contentType(MediaType.APPLICATION_JSON).content(jsonString)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		int sizeAfterDelete  = droneRepository.findAll().size();
		
		Assert.assertEquals(sizeBeforeDelete, 1);
		Assert.assertEquals(sizeAfterDelete, 0);
	}
	
	@Test()
	public void testDeleteIdNotExists() throws Exception{
		// Setup
		Drone newDrone = new Drone("Helicopter", new Date(2018 - 12 - 04));
		droneRepository.save(newDrone);
		String jsonString = "{\"title\":\"Helicopter\", \"firstFlight\": \"2018-12-04\"}";
		Long id = 1L;
		
		int sizeBeforeDelete = droneRepository.findAll().size();

		// Exercise
		mvc.perform(delete("/drone/" + id));
		int sizeAfterDelete  = droneRepository.findAll().size();
//		
		Assert.assertEquals(sizeBeforeDelete, 1);
		Assert.assertEquals(sizeAfterDelete, 1);
	}
	
	@Test()
	public void testUpdate() throws Exception{
		// Setup
		Drone newDrone = new Drone("Helicopter", new Date(2018 - 12 - 04));
		droneRepository.save(newDrone);
		Long id = newDrone.getId();
		
		String jsonString = "{\"title\":\"Quadcopter\", \"firstFlight\": \"2018-12-05\"}";
		//Long id = 1L;
		
		int sizeBeforeDelete = droneRepository.findAll().size();

		// Exercise
		mvc.perform(put("/drone/" + id ));
		int sizeAfterDelete  = droneRepository.findAll().size();
//		
		Assert.assertEquals(sizeBeforeDelete, 1);
		Assert.assertEquals(sizeAfterDelete, 1);
	}

}
