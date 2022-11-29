package com.dev.mateus.testesautomatizados.web;

import com.dev.mateus.testesautomatizados.domain.Planet;
import com.dev.mateus.testesautomatizados.services.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {

    final PlanetService service;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id){
        service.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Planet>> getByFilter(String terraim, String climate){
        return ResponseEntity.ok(service.list(terraim, climate));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> get(@PathVariable String name){
        return service
                .getByName(name)
                .map(planet -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> get(@PathVariable Long id){
        return service
                .get(id)
                .map(planet -> ResponseEntity.ok(planet))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody @Valid Planet planet) {
        Planet saved = service.createPlanet(planet);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
