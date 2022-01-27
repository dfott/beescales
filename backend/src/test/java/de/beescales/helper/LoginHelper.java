package de.beescales.helper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.beescales.data.Token;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

public class LoginHelper {

  private static final String USERNAME = "beescale";
  private static final String PASSWORD = "password123";

  private final MockMvc mockMvc;

  public LoginHelper(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  public Token login() throws Exception {
    String jsonBody =
        String.format("{\"username\":\"%s\", \"password\":\"%s\"}", USERNAME, PASSWORD);
    MvcResult result =
        mockMvc
            .perform(post("/auth").contentType("application/json").content(jsonBody))
            .andExpect(status().isOk())
            .andReturn();
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(result.getResponse().getContentAsString(), Token.class);
  }
}
