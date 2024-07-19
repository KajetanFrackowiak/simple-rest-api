package com.example.demo;

import com.example.demo.model.UserAccount;
import com.example.demo.repository.UserAccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testCreateUserAccount() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("testUser");
		userAccount.setGender("Male");
		userAccount.setAge(22);
		userAccount.setCreatedTimestamp(LocalDateTime.now());

		mockMvc.perform(post("/api/users")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(userAccount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("testUser"))
				.andExpect(jsonPath("$.gender").value("Male"))
				.andExpect(jsonPath("$.age").value(22));
	}

	@Test
	public void testGetUserAccount() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("testUser");
		userAccount.setGender("Male");
		userAccount.setAge(22);
		userAccount.setCreatedTimestamp(LocalDateTime.now());
		userAccount = userAccountRepository.save(userAccount);

		assertNotNull(userAccount.getId());

		mockMvc.perform(get("/api/users/" + userAccount.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username").value("testUser"))
				.andExpect(jsonPath("$.gender").value("Male"))
				.andExpect(jsonPath("$.age").value(22));
	}

	@Test
	public void testUpdateUserAccount() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("testUser");
		userAccount.setGender("Male");
		userAccount.setAge(22);
		userAccount.setCreatedTimestamp(LocalDateTime.now());
		userAccount = userAccountRepository.save(userAccount);

		UserAccount updateUser = new UserAccount();
		updateUser.setGender("Female");
		updateUser.setAge(23);
		updateUser.setCreatedTimestamp(userAccount.getCreatedTimestamp());

		mockMvc.perform(put("/api/users/" + userAccount.getId())
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updateUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.gender").value("Female"))
				.andExpect(jsonPath("$.age").value(23));
	}

	@Test
	public void testDeleteUserAccount() throws Exception {
		UserAccount userAccount = new UserAccount();
		userAccount.setUsername("testUser");
		userAccount.setGender("Male");
		userAccount.setAge(25);
		userAccount.setCreatedTimestamp(LocalDateTime.now());
		userAccount = userAccountRepository.save(userAccount);

		mockMvc.perform(delete("/api/users/" + userAccount.getId()))
				.andExpect(status().isNoContent());

		Optional<UserAccount> deletedUser = userAccountRepository.findById(userAccount.getId());
		assert (deletedUser.isEmpty());
	}
}