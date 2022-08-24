package frba.utn.tacs.matches.controller;

import frba.utn.tacs.matches.organizer.controller.MatchController;
import frba.utn.tacs.matches.organizer.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes={MatchService.class,MatchController.class})
@AutoConfigureMockMvc
class MatchControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	void matchesRetrieved() throws Exception {
		this.mvc.perform(MockMvcRequestBuilders.get("/matches").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}

}
