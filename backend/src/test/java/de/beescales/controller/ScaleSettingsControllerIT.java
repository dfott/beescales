package de.beescales.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.beescales.data.ScaleDetail;
import de.beescales.data.Token;
import de.beescales.helper.LoginHelper;
import de.beescales.repository.ScaleDetailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
class ScaleSettingsControllerIT {

  @Autowired MockMvc mockMvc;

  @Autowired ScaleDetailRepository detailRepository;

  LoginHelper loginHelper;

  @BeforeEach
  void setup() {
    loginHelper = new LoginHelper(mockMvc);
  }

  @Test
  void getAll() throws Exception {
    mockMvc
        .perform(get("/api/settings").header("Authorization", getAuthHeader()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].name").value("zu hause"))
        .andExpect(jsonPath("$[0].scales").isArray())
        .andExpect(jsonPath("$[0].scales", hasSize(1)))
        .andExpect(jsonPath("$[1].name").value("in der uni"))
        .andExpect(jsonPath("$[1].scales").isArray())
        .andExpect(jsonPath("$[1].scales", hasSize(2)));
  }

  @Test
  void updateMissingDeviceDetailsFails() throws Exception {
    long deviceId = 321L;
    String requestBody = "{\"color\":\"#FFFFFF\", \"name\":\"Geraet\"}";
    mockMvc
        .perform(
            put("/api/settings/" + deviceId)
                .header("Authorization", getAuthHeader())
                .contentType("application/json")
                .content(requestBody))
        .andExpect(status().isNotFound());
  }

  @Test
  void updateDeviceDetails() throws Exception {
    long deviceId = 12L;
    String color = "#123123";
    String name = "Neues Geraet";
    String requestBody = String.format("{\"color\":\"%s\", \"name\":\"%s\"}", color, name);
    mockMvc
        .perform(
            put("/api/settings/" + deviceId)
                .header("Authorization", getAuthHeader())
                .contentType("application/json")
                .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.deviceId").value(deviceId))
        .andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.color").value(color));

    Optional<ScaleDetail> scaleDetail = detailRepository.findById(deviceId);

    assertThat(scaleDetail)
        .isPresent()
        .get()
        .extracting("name", "color")
        .containsExactly(name, color);
  }

  private String getAuthHeader() throws Exception {
    Token token = loginHelper.login();
    return "Bearer " + token.getData();
  }
}
