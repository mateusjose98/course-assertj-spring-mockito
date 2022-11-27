package com.dev.mateus.testesautomatizados.services;

import static com.dev.mateus.testesautomatizados.common.PlanetConstants.*;
import com.dev.mateus.testesautomatizados.domain.Planet;
import static org.assertj.core.api.Assertions.*;

import com.dev.mateus.testesautomatizados.domain.PlanetRepository;
import com.dev.mateus.testesautomatizados.domain.QueryBuilder;
import com.dev.mateus.testesautomatizados.services.impl.PlanetServiceImpl;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

//@SpringBootTest(classes = {PlanetServiceImpl.class})
@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    @InjectMocks
    private PlanetServiceImpl planetService;
    @Mock
    private PlanetRepository planetRepository;

    @Test
    public void createPlanet_WithValidData_ReturnsOnePlanet() {
        when(planetRepository.save(PLANET)).thenReturn(PLANET);
        Planet sut = planetService
                .createPlanet(PLANET);
        assertThat(sut).isEqualTo(PLANET);

    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException() {
        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);
        assertThatThrownBy(
                () -> planetService.createPlanet(INVALID_PLANET)
        ).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() {
        when(planetRepository.findById(EXISTING_ID)).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.get(EXISTING_ID);
        assertThat(sut).isPresent();

    }

    @Test
    public void getPlanet_ByUnexistingId_ReturnSEmpty() {

        when(planetRepository.findById(UNEXISTING_ID)).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.get(UNEXISTING_ID);
        assertThat(sut).isNotPresent();
    }

    @Test
    public void getPlanetByName_ByExistingName_ReturnsPlanet() {
        when(planetRepository.findByNameIgnoreCase(EXISTING_NAME)).thenReturn(Optional.of(PLANET));
        Optional<Planet> sut = planetService.getByName(EXISTING_NAME);
        assertThat(sut).isPresent();
    }

    @Test
    public void getPlanetByName_ByNonExistingName_ReturnsPlanet() {
        when(planetRepository.findByNameIgnoreCase(UNEXISTING_NAME)).thenReturn(Optional.empty());
        Optional<Planet> sut = planetService.getByName(UNEXISTING_NAME);
        assertThat(sut).isNotPresent();
    }

    @Test
    public void listPlanets_ReturnsAllPlanets(){
        List<Planet> planetList = Arrays.asList(PLANET);
        var query = QueryBuilder.makeQuery(new Planet(PLANET.getTerrain(), PLANET.getClimate()));
        when(planetRepository.findAll(query)).thenReturn(planetList);
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());

        assertThat(sut).isNotEmpty();
        assertThat(sut).hasSize(1);
        assertThat(sut.get(0)).isEqualTo(PLANET);

    }

    @Test
    public void listPlanets_ReturnsNoPlanets(){

        when(planetRepository.findAll(any())).thenReturn(Collections.emptyList());
        List<Planet> sut = planetService.list(PLANET.getTerrain(), PLANET.getClimate());
        assertThat(sut).isEmpty();

    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowException(){
        assertThatCode(() -> planetService.removeById(EXISTING_ID)).doesNotThrowAnyException();

    }

    @Test
    public void removePlanet_WithNonExistingId_ThrowException(){

        doThrow(new RuntimeException()).when(planetRepository).deleteById(UNEXISTING_ID);
        assertThatThrownBy(() -> planetService.removeById(UNEXISTING_ID)).isInstanceOf(RuntimeException.class);
    }


}
