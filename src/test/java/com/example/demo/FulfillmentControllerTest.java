package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest
public class FulfillmentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProfileRepository profileRepository;

	@Test
	public void fulfill() throws Exception {
		Mockito.when(profileRepository.findByName(ArgumentMatchers.anyString()))
				.thenReturn(Optional.of(new Profile(1L, "小川", LocalDate.of(1981, 1, 8), BloodType.A, "元気です")));

		byte[] json = Files.readAllBytes(Paths.get(new ClassPathResource("sample.json").getURI()));

		mockMvc.perform(MockMvcRequestBuilders.post("/fulfillment")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("@.fulfillmentText").value("小川さんの血液型はA型です"));
	}
}
