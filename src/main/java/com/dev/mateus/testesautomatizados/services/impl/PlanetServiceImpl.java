package com.dev.mateus.testesautomatizados.services.impl;

import com.dev.mateus.testesautomatizados.domain.Planet;
import com.dev.mateus.testesautomatizados.domain.PlanetRepository;
import com.dev.mateus.testesautomatizados.domain.QueryBuilder;
import com.dev.mateus.testesautomatizados.services.PlanetService;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    final PlanetRepository repository;


    @Override
    public Planet createPlanet(Planet planet) {
        return repository.save(planet);
    }

    @Override
    public Optional<Planet> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Planet> getByName(String name) {
        return repository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Planet> list(String terraim, String climate) {
        var query = QueryBuilder.makeQuery(new Planet(terraim, climate));
        return repository.findAll(query);
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }
}
