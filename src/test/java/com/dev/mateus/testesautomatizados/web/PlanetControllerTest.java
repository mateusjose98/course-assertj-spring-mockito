package com.dev.mateus.testesautomatizados.web;

import com.dev.mateus.testesautomatizados.services.PlanetService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static com.dev.mateus.testesautomatizados.common.PlanetConstants.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_ReturnsCreated() throws Exception {

        when(planetService.createPlanet(PLANET)).thenReturn(PLANET);

        var result = mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$").value(PLANET));
    }

    @Test
    public void createPlanet_WithInvalidPlanet_ReturnsBadRequest() throws Exception {

        var resultNullPlanet = mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(NULL_PLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        var resultEmptyPlanet = mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(EMPTY_PLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        resultEmptyPlanet.andExpect(status().isUnprocessableEntity());
        resultNullPlanet.andExpect(status().isUnprocessableEntity());

    }

    @Test
    public void createPlanet_WithExistingNmae_ReturnsConflict() throws Exception {
        when(planetService.createPlanet(any())).thenThrow(DataIntegrityViolationException.class);
        var result = mockMvc.perform(
                post("/planets")
                        .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isConflict());

    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanetOk() throws Exception {
        when(planetService.get(EXISTING_ID)).thenReturn(Optional.of(PLANET));
        var result = mockMvc.perform(
                get("/planets/{id}", EXISTING_ID)
                        .content(objectMapper.writeValueAsString(PLANET))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.name").exists());


    }

    @Test
    public void getPlanet_ByNonExistingId_ReturnsNotFound() throws Exception {
        when(planetService.get(UNEXISTING_ID)).thenReturn(Optional.empty());
        var result = mockMvc.perform(get("/planets/{id}", UNEXISTING_ID)
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void getPlanet_ByExistingName_ReturnsPlanetOk() throws Exception {

        when(planetService
                .getByName(
                        PLANET.getName()))
                .thenReturn(Optional.of(PLANET));

        var result = mockMvc.perform(
                get("/planets/name/{nome}", PLANET.getName())
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$").exists());


    }

    @Test
    public void getPlanet_ByNonExistingName_ReturnsNotFound() throws Exception {
       // não precisa mockar
        var result = mockMvc.perform(get("/planets/name/{nome}", "nome")
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }


}
