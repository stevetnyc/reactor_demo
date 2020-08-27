package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class DemoControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
		DemoController controller = new DemoController();
		assertThat(controller).isNotNull();
	}

	@Test
	public void givenValidPOJOList_whenGetAll_thenCorrectNumberOfElementsReturned() throws Exception {
			List<DemoPOJO> pjList = DemoPOJOService.getAll();
		if (pjList != null && pjList.size() == 0) {
			log.info("Empty POJO List - initializing");
			DemoPOJOService.createInitialState(5);
		}
		int numElements = pjList.size();
		log.info("Number of elements = {}", numElements);
		mockMvc.perform(MockMvcRequestBuilders
				.get("/objects/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*", hasSize(numElements)));
	}

	@Test
	public void givenEmptyPOJOList_whenGetALL_then204() throws Exception {
		List<DemoPOJO> pjList = DemoPOJOService.getAll();
		if (pjList != null) {
			DemoPOJOService.eraseState();
		}
		mockMvc.perform(MockMvcRequestBuilders
				.get("/objects/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().is(204));
	}

	@Test
	public void givenValidObjectId_whenGetObject_thenCorrectObjectReturn() throws Exception {

		var objId = 4;

		mockMvc.perform(MockMvcRequestBuilders
				.get("/objects/{id}", objId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(objId));
	}

	@Test
	public void givenInvalidObjectId_whenGetObject_then404() throws Exception {
		var objId = -999;

		mockMvc.perform(MockMvcRequestBuilders
				.get("/objects/{id}", objId)
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());

	}

	@Test
	public void givenValidPojoList_whenMonoRouteCalled_thenElement1Returned() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders
				.get("/stream/mono/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));

	}
}
