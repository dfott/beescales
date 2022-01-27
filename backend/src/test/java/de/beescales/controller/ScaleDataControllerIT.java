package de.beescales.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.beescales.AbstractDatabaseIT;
import de.beescales.data.Token;
import de.beescales.helper.LoginHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
class ScaleDataControllerIT extends AbstractDatabaseIT {

  @Autowired MockMvc mockMvc;
  LoginHelper loginHelper;

  @BeforeEach
  void setup() {
    this.loginHelper = new LoginHelper(this.mockMvc);
  }

  @Test
  void shouldReturnAllWeightData() throws Exception {
    performRequest("/api/data")
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[1].deviceId").value(1234))
        .andExpect(jsonPath("$[1].data").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"]").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"].data", hasSize(3)))
        .andExpect(jsonPath("$[2].deviceId").value(5678))
        .andExpect(jsonPath("$[2][\"data\"][\"2021-11-23\"]").isMap())
        .andExpect(jsonPath("$[2][\"data\"][\"2021-11-23\"].data", hasSize(2)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void shouldReturnAnonymizedWeightData() throws Exception {
    mockMvc
        .perform(get("/api/data"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].deviceId").value(12))
        .andExpect(jsonPath("$[0].deviceName").value("Waage 1"))
        .andExpect(jsonPath("$[0].locationName").value("Standort 1"))
        .andExpect(jsonPath("$[1].deviceId").value(1234))
        .andExpect(jsonPath("$[1].deviceName").value("Waage 2"))
        .andExpect(jsonPath("$[1].locationName").value("Standort 2"))
        .andExpect(jsonPath("$[2].deviceId").value(5678))
        .andExpect(jsonPath("$[2].deviceName").value("Waage 3"))
        .andExpect(jsonPath("$[2].locationName").value("Standort 1"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnScaleDataForAGivenDevice() throws Exception {
    long deviceId = 5678;
    performRequest("/api/data/" + deviceId,"2021-11-01T01:01:01Z","2021-11-31T01:01:01Z")
            .andExpect(jsonPath("$.deviceId").value(deviceId))
            .andExpect(jsonPath("$.data").isMap())
            .andExpect(jsonPath("$[\"data\"][\"2021-11-23\"]").isMap())
            .andExpect(jsonPath("$[\"data\"][\"2021-11-23\"].data", hasSize(2)))
            .andExpect(status().isOk());
  }

  @Test
  void shouldReturnWeightDataFromAndToASpecificDate() throws Exception {

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("from", Collections.singletonList("2021-11-23T01:00:00Z"));
    params.put("to", Collections.singletonList("2021-11-23T02:00:00Z"));
    performRequest("/api/data", params)
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[1].data").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"]").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"].data", hasSize(2)))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  void shouldAllReturnWeightDataIfRequestParamsOnlyContainsFrom() throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("from", Collections.singletonList("2021-05-05T10:09:10Z"));
    performRequest("/api/data")
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[1].deviceId").value(1234))
        .andExpect(jsonPath("$[1].data").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"]").isMap())
        .andExpect(jsonPath("$[1][\"data\"][\"2021-11-23\"].data", hasSize(3)))
        .andExpect(status().isOk())
        .andReturn();
  }

  private ResultActions performRequest(String uri, MultiValueMap<String, String> params)
      throws Exception {
    Token token = loginHelper.login();
    return mockMvc.perform(
        get(uri).header("Authorization", "Bearer " + token.getData()).params(params));
  }

  private ResultActions performRequest(String uri, String from, String to) throws Exception {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.put("from", Collections.singletonList(from));
    params.put("to", Collections.singletonList(to));
    return performRequest(uri, params);
  }

  private ResultActions performRequest(String uri) throws Exception {
    return performRequest(uri, new LinkedMultiValueMap<>());
  }
}
