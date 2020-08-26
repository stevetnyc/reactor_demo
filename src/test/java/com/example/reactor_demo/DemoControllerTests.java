package com.example.reactor_demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		DemoController controller = new DemoController();
		assertThat(controller).isNotNull();
	}

	@Test
	public void givenObjectId_whenGetObject_thenCorrectObjectReturn() throws Exception {

		var objId = 3;

		mockMvc.perform(MockMvcRequestBuilders
				.get("/object/{id}", objId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk());
	}
}