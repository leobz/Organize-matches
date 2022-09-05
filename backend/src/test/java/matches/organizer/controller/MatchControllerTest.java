package matches.organizer.controller;

import matches.organizer.service.MatchService;
import matches.organizer.storage.InMemoryMatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={MatchService.class,MatchController.class, InMemoryMatchRepository.class})
@AutoConfigureMockMvc
class MatchControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void matchesRetrieved() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/matches").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

	@Test
	void matchesPostMethodOK() throws Exception {

		this.mvc.perform(MockMvcRequestBuilders.post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"location\": \"GRUN FC\",\n" +
				"   \"date\": \"2023-09-04\",\n" +
				"   \"hour\": \"17:00:00\"\n" +
				"}")).andExpect(status().isOk());

	}
	@Test
	void matchesPostMethodIncomplete() throws Exception {
	//TODO Configurar como BAD_REQUEST
		this.mvc.perform(MockMvcRequestBuilders.post("/matches").contentType(MediaType.APPLICATION_JSON).content("{\n" +
				"   \"name\": \"un Partido de prueba\",\n" +
				"   \"date\": \"2023-09-04\",\n" +
				"   \"hour\": \"17:00:00\"\n" +
				"}")).andExpect(status().isInternalServerError());

	}

}
