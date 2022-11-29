package com.dev.mateus.testesautomatizados.services;

import com.dev.mateus.testesautomatizados.domain.Planet;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface PlanetService {
    Planet createPlanet(Planet planet);

    Optional<Planet> get(Long id);

    Optional<Planet> getByName(String name);

    List<Planet> list(String terraim, String climate);

    void removeById(Long id);

}
